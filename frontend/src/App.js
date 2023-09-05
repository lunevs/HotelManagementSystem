import './App.css';
import LoginBox from "./components/auth/LoginBox";
import React, {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import User from "./components/users/User";
import Location from "./components/locations/Location";
import Amenity from "./components/amenities/Amenity";
import Status from "./components/utils/Status";
import Navigation from "./components/utils/Navigation";
import LogoutBox from "./components/auth/LogoutBox";
import MainSearch from "./components/bookings/MainSearch";
import LocationDetails from "./components/locations/LocationDetails";
import BookingsList from "./components/bookings/BookingsList";

function App() {

    const [token, setToken] = useState('');
    const [status, setStatus] = useState({
        message: '',
        type: ''
    });

    useEffect(() => {
        const loggedUserJSON = window.localStorage.getItem('loggedHotelServiceUser');
        if (loggedUserJSON) {
            const savedToken = JSON.parse(loggedUserJSON);
            setToken(savedToken.token);
        }
    }, [])



    const changeStatusHandler = ({message, type}) => {
        setStatus({message, type});
        setTimeout(() => setStatus({message: '', type: ''}), 2000);
    }

  return (
      <Router>
          <div className="container text-center mb-4">
              {
                  token === '' ?
                      <div className="row">
                          <div className="col border border-light m-2 p-3 rounded-2 shadow-sm">
                              <LoginBox changeTokenHandler={setToken} changeStatusHandler={changeStatusHandler} />
                          </div>
                      </div>
                      :
                      <div>
                          <Navigation isAuth={token !== ''} />
                          <Status message={status.message} type={status.type} />
                          <Routes>
                              <Route path='/' element={<MainSearch token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/search' element={<MainSearch token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/bookings' element={<BookingsList token={token} />} />
                              <Route path='/mylocations' element={<Location token={token} />} />
                              <Route path='/addlocation' element={<Location token={token} />} />
                              <Route path='/editlocation' element={<Location token={token} />} />
                              <Route path='/locationdetails/:id' element={<LocationDetails token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/addroom' element={<Location token={token} />} />
                              <Route path='/editroom' element={<Location token={token} />} />
                              <Route path='/accounts' element={<User token={token} />} />
                              <Route path='/amenities' element={<Amenity token={token} />} />
                              <Route path='/logout' element={<LogoutBox changeTokenHandler={setToken} />} />
                              <Route path='/login' element={<LoginBox changeTokenHandler={setToken} changeStatusHandler={changeStatusHandler} />} />
                          </Routes>
                      </div>
              }
          </div>
      </Router>
  );
}

export default App;
