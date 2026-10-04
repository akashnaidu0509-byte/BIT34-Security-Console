\# BIT34 Threat Model



\## 1. System Assets



The main assets are:



\- Security event records

\- Alert records

\- Analyst investigation notes

\- PostgreSQL database

\- Kafka event stream

\- Monitoring dashboard



\## 2. Threats



\### Malicious or Invalid Event Injection



An attacker could submit false or malformed security events through the event API.



\### Event Replay



The same security event could be submitted repeatedly.



The system uses `eventKey` to provide idempotency and reduce duplicate event creation.



\### Database Exposure



Unauthorized access to PostgreSQL could expose security events, alerts, and analyst notes.



\### Kafka Message Exposure



Unauthorized access to Kafka could allow an attacker to observe security event messages.



\### Denial of Service



A large number of API requests or security events could increase database and application load.



\## 3. Security Controls



The prototype includes:



\- Unique event keys for replay protection

\- PostgreSQL persistence

\- Kafka-based asynchronous event streaming

\- Detection rules for repeated failed logins

\- Automated detection testing

\- Docker-based reproducible infrastructure

\- Separate API endpoints for events, alerts, notes, graph analysis, and baseline analysis



\## 4. Residual Risks



The current prototype does not implement full production authentication, authorization, encryption, rate limiting, or enterprise-grade access control.



These are identified as areas for future hardening.

