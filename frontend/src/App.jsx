import { useEffect, useState } from "react";
import "./App.css";

function App() {
  const [dashboard, setDashboard] = useState({
    totalEvents: 0,
    totalAlerts: 0,
  });

  const [alerts, setAlerts] = useState([]);
  const [events, setEvents] = useState([]);
const [timeline, setTimeline] = useState([]);
const [timelineIp, setTimelineIp] = useState("");

  const loadData = async () => {
    try {
      const dashboardResponse = await fetch(
        "http://localhost:8080/api/dashboard"
      );
      const dashboardData = await dashboardResponse.json();
      setDashboard(dashboardData);

      const alertsResponse = await fetch(
        "http://localhost:8080/api/alerts"
      );
      const alertsData = await alertsResponse.json();
      setAlerts(alertsData);

      const eventsResponse = await fetch(
        "http://localhost:8080/api/events"
      );
      const eventsData = await eventsResponse.json();
      setEvents(eventsData);

if (eventsData.length > 0) {
  const ip = eventsData[0].sourceIp;
  setTimelineIp(ip);

  const timelineResponse = await fetch(
    `http://localhost:8080/api/timeline/${encodeURIComponent(ip)}`
  );

  const timelineData = await timelineResponse.json();
  setTimeline(timelineData);
}

    } catch (error) {
      console.error("Could not load backend data:", error);
    }
  };

  useEffect(() => {
    loadData();

    const timer = setInterval(loadData, 5000);

    return () => clearInterval(timer);
  }, []);

  return (
    <div className="dashboard">
      <header>
        <h1>BIT34 Security Monitoring Console</h1>
        <p>Streaming Network Detection and Response</p>
      </header>

      <section className="cards">
        <div className="card">
          <h3>Total Events</h3>
          <strong>{dashboard.totalEvents}</strong>
        </div>

        <div className="card">
          <h3>Total Alerts</h3>
          <strong>{dashboard.totalAlerts}</strong>
        </div>

        <div className="card">
          <h3>System Status</h3>
          <strong className="online">ONLINE</strong>
        </div>
      </section>

      <section className="panel">
        <h2>Security Alerts</h2>

        {alerts.length === 0 ? (
          <p>No alerts detected.</p>
        ) : (
          alerts.map((alert) => (
            <div className="alert" key={alert.id}>
              <div>
                <strong>{alert.severity}</strong>
                <span>{alert.message}</span>
              </div>

              <div>
                <span>Source: {alert.sourceIp}</span>
                <span>
                  {new Date(alert.timestamp).toLocaleString()}
                </span>
              </div>
            </div>
          ))
        )}
      </section>

      <section className="panel">
        <h2>Recent Security Events</h2>

        {events.map((event) => (
          <div className="event" key={event.id}>
            <strong>{event.eventType}</strong>
            <span>{event.sourceIp}</span>
            <span>{event.username}</span>
            <span>
              {new Date(event.timestamp).toLocaleString()}
            </span>
          </div>
        ))}
      </section>

      <section className="panel">
  <h2>Incident Timeline</h2>

  {timeline.length === 0 ? (
    <p>No timeline events available.</p>
  ) : (
    <div>
      <p>
        Timeline for source IP: <strong>{timelineIp}</strong>
      </p>

      {timeline.map((event) => (
        <div className="event" key={event.id}>
          <strong>{event.eventType}</strong>
          <span>{event.username}</span>
          <span>{new Date(event.timestamp).toLocaleString()}</span>
        </div>
      ))}
    </div>
  )}
</section>
    </div>
  );
}

export default App;