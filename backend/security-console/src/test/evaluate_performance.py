import json
import time
import urllib.request
import statistics
from datetime import datetime, timezone


BASE_URL = "http://localhost:8080"

ATTACK_IPS = [
    "10.10.0.1",
    "10.10.0.2",
    "10.10.0.3",
    "10.10.0.4",
    "10.10.0.5",
]

BENIGN_IPS = [
    "10.20.0.1",
    "10.20.0.2",
    "10.20.0.3",
    "10.20.0.4",
    "10.20.0.5",
]


def post_event(event):
    data = json.dumps(event).encode("utf-8")

    request = urllib.request.Request(
        f"{BASE_URL}/api/events",
        data=data,
        headers={"Content-Type": "application/json"},
        method="POST",
    )

    start = time.perf_counter()

    with urllib.request.urlopen(request) as response:
        response.read()

    end = time.perf_counter()

    return end - start


def get_alerts():
    with urllib.request.urlopen(f"{BASE_URL}/api/alerts") as response:
        return json.loads(response.read().decode("utf-8"))


def make_event(ip, event_type, number):
    return {
        "eventKey": f"EVAL-{ip}-{event_type}-{number}-{time.time_ns()}",
        "eventType": event_type,
        "sourceIp": ip,
        "username": "evaluation-user",
        "timestamp": datetime.now().replace(microsecond=0).isoformat(),
        "details": "Automated evaluation workload",
    }


def main():

    print("BIT34 Evaluation")
    print("================")
    print()

    latencies = []

    total_events = 0

    start_all = time.perf_counter()

    print("Sending attack scenarios...")

    for ip in ATTACK_IPS:
        for i in range(5):
            latency = post_event(
                make_event(ip, "LOGIN_FAILED", i + 1)
            )

            latencies.append(latency)
            total_events += 1

    print("Sending benign scenarios...")

    for ip in BENIGN_IPS:
        for i in range(5):
            latency = post_event(
                make_event(ip, "LOGIN_SUCCESS", i + 1)
            )

            latencies.append(latency)
            total_events += 1

    end_all = time.perf_counter()

    total_time = end_all - start_all

    alerts = get_alerts()

    alert_ips = {
        alert["sourceIp"]
        for alert in alerts
    }

    detected_attacks = sum(
        1 for ip in ATTACK_IPS
        if ip in alert_ips
    )

    false_positive_ips = sum(
        1 for ip in BENIGN_IPS
        if ip in alert_ips
    )

    detection_rate = (
        detected_attacks / len(ATTACK_IPS)
    ) * 100

    false_positive_rate = (
        false_positive_ips / len(BENIGN_IPS)
    ) * 100

    average_latency = statistics.mean(latencies)

    sorted_latencies = sorted(latencies)

    p95_index = int(len(sorted_latencies) * 0.95) - 1
    p95_latency = sorted_latencies[max(0, p95_index)]

    throughput = total_events / total_time

    print()
    print("Evaluation Results")
    print("==================")
    print(f"Total events: {total_events}")
    print(f"Attack scenarios: {len(ATTACK_IPS)}")
    print(f"Detected attacks: {detected_attacks}")
    print(f"Detection rate: {detection_rate:.2f}%")
    print(f"Benign scenarios: {len(BENIGN_IPS)}")
    print(f"False-positive scenarios: {false_positive_ips}")
    print(f"False-positive rate: {false_positive_rate:.2f}%")
    print(f"Average latency: {average_latency * 1000:.2f} ms")
    print(f"P95 latency: {p95_latency * 1000:.2f} ms")
    print(f"Throughput: {throughput:.2f} events/sec")
    print()
    print("Evaluation complete.")


if __name__ == "__main__":
    main()