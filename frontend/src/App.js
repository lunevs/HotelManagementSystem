import './App.css';
import LoginBox from "./components/auth/LoginBox";
import React, {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";

import Status from "./components/utils/Status";
import Navigation from "./components/utils/Navigation";
import LogoutBox from "./components/auth/LogoutBox";
import MainSearch from "./components/bookings/MainSearch";
import LocationDetails from "./components/locations/LocationDetails";
import BookingsList from "./components/bookings/BookingsList";
import LocationsTable from "./components/locations/LocationsTable";
import LocationCreateForm from "./components/locations/LocationCreateForm";
import LocationEditForm from "./components/locations/LocationEditForm";
import LocationAddRoomForm from "./components/locations/LocationAddRoomForm";
import LocationEditRoomForm from "./components/locations/LocationEditRoomForm";
import UserCreateForm from "./components/users/UserCreateForm";
import UserEditForm from "./components/users/UserEditForm";
import UserChangeRole from "./components/users/UserChangeRole";
import AmenityAddForm from "./components/amenities/AmenityAddForm";
import AmenityListAndUpdate from "./components/amenities/AmenityListAndUpdate";
import BoxDiv from "./components/utils/style/BoxDiv";

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
                          <Status message={status.message} type={status.type} />
                          <BoxDiv>
                              <LoginBox changeTokenHandler={setToken} changeStatusHandler={changeStatusHandler} />
                          </BoxDiv>
                      </div>
                      :
                      <div>
                          <Navigation isAuth={token !== ''} />
                          <Status message={status.message} type={status.type} />
                          <Routes>
                              <Route path='/' element={<MainSearch token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/search' element={<MainSearch token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/bookings' element={<BookingsList token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/mylocations' element={<LocationsTable token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/addlocation' element={<LocationCreateForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/editlocation' element={<LocationEditForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/locationdetails/:id' element={<LocationDetails token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/addroom' element={<LocationAddRoomForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/editroom' element={<LocationEditRoomForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/addaccount' element={<UserCreateForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/editaccount' element={<UserEditForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/editaccountrole' element={<UserChangeRole token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/addamenity' element={<AmenityAddForm token={token} changeStatusHandler={changeStatusHandler} />} />
                              <Route path='/editamenity' element={<AmenityListAndUpdate token={token} changeStatusHandler={changeStatusHandler} />} />
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
