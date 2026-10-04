import json
import random
from datetime import datetime, timezone

ips = ["192.168.1.50", "10.0.0.55", "10.0.0.77", "10.0.0.88"]
users = ["admin", "testuser", "kafkauser", "replaytest"]

for i in range(20):
    event = {
        "eventType": random.choice(["LOGIN_FAILED", "LOGIN_SUCCESS"]),
        "sourceIp": random.choice(ips),
        "username": random.choice(users),
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "details": "Generated local security test event"
    }
    print(json.dumps(event))
