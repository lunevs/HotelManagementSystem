import './App.css';
import LoginBox from "./components/LoginBox";
import {useState} from "react";
import User from "./components/User";
import Location from "./components/Location";
import Amenity from "./components/Amenity";

function App() {

    const [token, setToken] = useState('');


  return (
    <div className="container text-center mb-4">
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <LoginBox changeTokenHandler={setToken} />
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Search locations and booking it</p>
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Amenities</p>
                <Amenity token={token} />
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Location management</p>
                <Location token={token} />
            </div>
        </div>
        <div className="row">
            <div className="col border border-dark m-2 p-3 rounded-2">
                <p className="fs-3 pb-3">Users management</p>
                <User token={token} />
            </div>
        </div>
    </div>
  );
}

export default App;
