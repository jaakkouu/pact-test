import { useEffect, useState } from 'react';
import { Application } from './generated';
import { ApplicationApi } from './services/ApplicationApi';

const client = new ApplicationApi();

function App() {
  const [applications, setApplications] = useState<Application[] | null | undefined>([]);

  useEffect(() => {
    client.getApplications().then((applications) => {
      setApplications(applications);
    }).catch(() => console.error("Error occured while fetching applications"));
  }, [applications]);

  const renderTable = () => {

    const dataError = applications === null;
    const dataLoading = applications === undefined;
    const noApplications = applications != null && !applications.length;

    const renderRow = (app: Application) => {
      const {id, title, created, attachments} = app;
      return <tr>
        <td>{id}</td>
        <td>{title}</td>
        <td>{created.toISOString()}</td>
        {attachments && <td>{attachments.length}</td>}
      </tr>
    }

    if(dataLoading) {
      return "Loading applications...";
    }

    if(dataError) {
      return "Something went wrong when loading applications...";
    }

    return noApplications ? <p>No applications found...</p> : <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Created</th>
            <th>Attachments</th>
          </tr>
        </thead>
      <tbody>
      { 
        applications.map(renderRow)
      }
      </tbody>
    </table>
  }

  return (
    <div>
      <h1>Applications</h1>
      { renderTable() }
    </div>
  );
}

export default App;
