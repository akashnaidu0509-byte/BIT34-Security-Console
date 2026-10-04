import json
import urllib.request


BASE_URL = "http://localhost:8080"


def get_json(path):
    with urllib.request.urlopen(BASE_URL + path) as response:
        return json.loads(response.read().decode("utf-8"))


def main():

    events = get_json("/api/events")
    baseline = get_json("/api/baseline")

    failed_by_ip = baseline["failedLoginsByIp"]
    average = baseline["averageFailedLoginsPerIp"]
    threshold = baseline["deviationThreshold"]
    baseline_flagged = set(baseline["flaggedIps"])

    # Static rule:
    # five or more failed logins from the same IP.
    static_flagged = {
        ip
        for ip, count in failed_by_ip.items()
        if count >= 5
    }

    baseline_only = baseline_flagged - static_flagged
    static_only = static_flagged - baseline_flagged
    both = baseline_flagged & static_flagged

    print()
    print("BIT34 Baseline vs Static Rules")
    print("==============================")
    print()

    print(f"Total events: {len(events)}")
    print(f"Average failed logins/IP: {average:.2f}")
    print(f"Baseline deviation threshold: {threshold:.2f}")
    print()

    print("Static rule: >= 5 failed logins/IP")
    print(f"Static-rule flagged IPs: {sorted(static_flagged)}")
    print()

    print("Behavioral baseline:")
    print(f"Baseline flagged IPs: {sorted(baseline_flagged)}")
    print()

    print(f"Flagged by both methods: {sorted(both)}")
    print(f"Baseline-only flags: {sorted(baseline_only)}")
    print(f"Static-rule-only flags: {sorted(static_only)}")
    print()

    print("Comparison Summary")
    print("==================")
    print(f"Static-rule flagged count: {len(static_flagged)}")
    print(f"Baseline flagged count: {len(baseline_flagged)}")
    print(f"Overlap count: {len(both)}")
    print()

    print("Comparison complete.")


if __name__ == "__main__":
    main()