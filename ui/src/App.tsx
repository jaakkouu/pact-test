import { useEffect, useState } from 'react';
import { Application } from './generated';
import './App.css';
import { ApplicationApi } from './services/ApplicationApi';

const client = new ApplicationApi();

function App() {
  const [applications, setApplications] = useState<Application[]>([]);

  useEffect(() => {
    client.getApplications().then((applications) => {
      setApplications(applications);
      console.log(applications);
    }).catch(() => console.error("Error occured while fetching applications"));
  }, [applications]);

  return (
    <div className="App">
      <header className="App-header">Applications</header>
      { applications.length ? <table><thead><tr><th>ID</th><th>Title</th><th>Created</th></tr></thead><tbody>{
        applications.map((app: Application) => <tr>
          <td>{app.id}</td>
          <td>{app.title}</td>
          <td>{app.created.toISOString()}</td>
        </tr>)
        }</tbody></table> : <p>Loading applications...</p>}
    </div>
  );
}

export default App;
