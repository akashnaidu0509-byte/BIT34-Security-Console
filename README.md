\# BIT34 Security Monitoring Console



\## 1. Project Overview



BIT34 Security Monitoring Console is a prototype for streaming network security event detection and response.



The system ingests security events, stores normalized events in PostgreSQL, publishes events through Apache Kafka, applies detection rules, generates alerts, and provides a React-based monitoring dashboard.

\## 2. Architecture



```text

Security Logs / API

&#x20;       |

&#x20;       v

Spring Boot Backend

&#x20;       |

&#x20;       +----> PostgreSQL

&#x20;       |

&#x20;       +----> Kafka Producer

&#x20;                 |

&#x20;                 v

&#x20;            Kafka Topic

&#x20;         security-events

&#x20;                 |

&#x20;                 v

&#x20;         Kafka Consumer

&#x20;       |

&#x20;       v

Detection \& Alerting

&#x20;       |

&#x20;       v

React Dashboard

\## 3. Technologies



\- Java 21

\- Spring Boot

\- Spring Data JPA

\- PostgreSQL

\- Apache Kafka

\- React

\- Vite

\- Docker

\- Maven

\- JUnit / Mockito



\## 4. Main Features



\### Security Event Ingestion

Security events can be submitted through the REST API and stored in PostgreSQL.



\### Detection Rule

The system detects repeated failed login attempts. Five or more `LOGIN\_FAILED` events from the same source IP generate a HIGH severity alert.



\### Kafka Streaming

Security events are published to the Kafka topic `security-events` and consumed asynchronously by the backend.



\### Alert Management

Detected security conditions are stored as alerts and displayed on the dashboard.



\### Monitoring Dashboard

The React dashboard displays total events, total alerts, system status, security alerts, and recent security events.



\### Analyst Notes

Analysts can attach investigation notes to alerts.



\### Replay and Idempotency

Events can contain a unique `eventKey`. Replaying an event with the same key does not create a duplicate event.



\## 5. API Endpoints



| Method | Endpoint | Purpose |

|---|---|---|

| GET | `/api/health` | Check backend status |

| GET | `/api/events` | Retrieve security events |

| POST | `/api/events` | Submit a security event |

| GET | `/api/alerts` | Retrieve alerts |

| POST | `/api/alerts` | Create an alert |

| GET | `/api/dashboard` | Retrieve dashboard statistics |

| GET | `/api/notes/alert/{alertId}` | Retrieve analyst notes |

| POST | `/api/notes` | Add an analyst note |



\## 6. Running the Project



\### Start PostgreSQL



```powershell

docker start bit34-postgres



docker start bit34-kafka



\### Start the Backend



Open a \*\*new PowerShell window\*\* and run:



```powershell

cd C:\\Users\\abc\\BIT34\\backend\\security-console

.\\mvnw.cmd spring-boot:run "-Dspring-boot.run.jvmArguments=-Duser.timezone=UTC"



\### Start the Frontend



Open another PowerShell window and run:



```powershell

cd C:\\Users\\abc\\BIT34\\frontend

npm run dev



\## 7. Testing



The detection logic has an automated unit test using JUnit and Mockito.



Run the detection test with:



```powershell

cd C:\\Users\\abc\\BIT34\\backend\\security-console

.\\mvnw.cmd -Dtest=DetectionServiceTest test



\## 8. Docker Reproducibility



The project includes a `docker-compose.yml` file containing the PostgreSQL and Kafka services required by the backend.



The containers can be started with:



```powershell

docker compose up -d



\## 9. Project Structure



```text

BIT34/

├── backend/

│   └── security-console/

│       ├── src/main/java/

│       │   └── com/bit34/security\_console/

│       │       ├── controller/

│       │       ├── model/

│       │       ├── repository/

│       │       ├── service/

│       │       └── config/

│       └── pom.xml

│

├── frontend/

│   ├── src/

│   │   ├── App.jsx

│   │   └── App.css

│   └── package.json

│

├── log-generator/

├── tests/

├── docs/

├── docker-compose.yml

└── README.md



\## 10. Project Status



The current prototype demonstrates:



\- REST-based security event ingestion

\- PostgreSQL event and alert storage

\- Kafka event streaming

\- Rule-based security detection

\- Alert generation

\- React monitoring dashboard

\- Analyst notes

\- Event replay protection using idempotency keys

\- Automated detection testing

\- Docker-based infrastructure configuration



The project is designed as a prototype for a streaming security monitoring and detection console.



\## 11. Graph-Based Analysis



The system provides a graph representation of security event relationships through:



`GET /api/graph`



The graph represents:



\- Source IP addresses as IP nodes

\- Usernames as USER nodes

\- Security events as relationships between IP and user nodes



For example, a failed login can be represented as:



```text

IP Address ── LOGIN\_FAILED ──> User



\## 12. Behavioral Baseline



The system provides a baseline analysis endpoint:



`GET /api/baseline`



The baseline groups failed login events by source IP and calculates the average number of failed logins per IP.



This provides a simple behavioral reference that can be used to identify unusually active source IP addresses.


## 13. Local Lab Log Generator

The project includes a Python-based local security log generator in:

`log-generator/generate_logs.py`

The generator produces synthetic `LOGIN_FAILED` and `LOGIN_SUCCESS` events with source IP addresses, usernames, timestamps, and event details.

It can be executed with:

```powershell
cd C:\Users\abc\BIT34\log-generator
python .\generate_logs.py

### Automated Test Result

The full Maven test suite was executed successfully with:

```powershell
.\mvnw.cmd clean test

## 14. Evaluation and Security Documentation

Additional project documentation is available in the `docs` directory:

- `docs/api-contract.md` — API and data contract
- `docs/threat-model.md` — system threats, controls, and residual risks
- `docs/evaluation.md` — observed prototype evaluation results

The project also includes a local laboratory log generator under:

`log-generator/generate_logs.py`

## 15. CI/CD

The project includes a GitHub Actions workflow:

`.github/workflows/ci.yml`

The workflow automatically:

1. Sets up Java 21
2. Runs the complete Maven backend test suite
3. Sets up Node.js 20
4. Installs frontend dependencies
5. Builds the React frontend

The backend test suite was verified locally with `BUILD SUCCESS`.

The frontend production build was also verified successfully with `npm run build`.