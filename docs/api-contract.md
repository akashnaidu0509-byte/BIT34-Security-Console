\# BIT34 API Contract



\## Health



\### GET /api/health



Returns the current backend status.



Example response:



BIT34 Security Console is running



\## Security Events



\### GET /api/events



Returns stored security events.



\### POST /api/events



Creates a security event.



Example request:



{

&#x20; "eventType": "LOGIN\_FAILED",

&#x20; "sourceIp": "192.168.1.50",

&#x20; "username": "admin",

&#x20; "timestamp": "2026-10-03T12:00:00",

&#x20; "details": "Invalid password",

&#x20; "eventKey": "EVENT-001"

}



The `eventKey` is used for replay protection and idempotency.



\## Alerts



\### GET /api/alerts



Returns detected security alerts.



\### POST /api/alerts



Creates an alert.



\## Dashboard



\### GET /api/dashboard



Returns dashboard statistics including total events and total alerts.



\## Analyst Notes



\### GET /api/notes/alert/{alertId}



Returns notes associated with an alert.



\### POST /api/notes



Creates an analyst investigation note.



\## Graph Analysis



\### GET /api/graph



Returns IP and user nodes with relationships derived from security events.



\## Behavioral Baseline



\### GET /api/baseline



Returns failed-login counts grouped by source IP and the average failed-login count per IP.

