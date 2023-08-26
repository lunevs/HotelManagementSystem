import './App.css';
import LoginBox from "./components/LoginBox";
import {useState} from "react";
import AccountsBox from "./components/AccountsBox";

function App() {

    const [token, setToken] = useState('');


  return (
    <div className="container text-center mb-4">
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <LoginBox changeTokenHandler={setToken} />
            </div>
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Amenities</p>
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Search locations</p>
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Location management</p>
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Users management</p>
                <AccountsBox token={token} />
            </div>
        </div>
    </div>
  );
}

export default App;
