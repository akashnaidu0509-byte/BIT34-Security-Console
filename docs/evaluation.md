\# BIT34 Evaluation Results



\## 1. Current System Counts



The dashboard was checked during project testing.



Observed result:



\- Total security events: 8

\- Total alerts: 1



API used:



`GET /api/dashboard`



\## 2. Detection Test



The detection unit test verifies that an alert is created after five failed login attempts from the same source IP.



Test command:



```powershell

.\\mvnw.cmd clean test

