import React, {useEffect, useState} from "react";
import LocationService from "../services/LocationService";
import LocationEditForm from "./LocationEditForm";
import LocationCreateForm from "./LocationCreateForm";
import LocationAddRoomForm from "./LocationAddRoomForm";
import LocationEditRoomForm from "./LocationEditRoomForm";

const Location = ({token}) => {

    const [locations, setLocations] = useState([]);
    const [resError, setResError] = useState('');
    const [reload, setReload] = useState(false);

    useEffect(() => {
        if (token !== '') {
            LocationService
                .getAllLocations(token)
                .then(result => {
                    console.log(result);
                    setLocations(result);
                })
                .catch(error => {
                    setResError(error.response.data.title);
                });
        }
    }, [token, reload]);



    if (token !== '' && resError === '') {
        return (
            <div>
                <LocationCreateForm token={token} locations={locations} setLocations={setLocations} />

                <LocationEditForm token={token} locations={locations} setLocations={setLocations} />

                <LocationAddRoomForm token={token} locations={locations} setReload={setReload}/>

                <LocationEditRoomForm token={token} locations={locations} setReload={setReload} />

            </div>
        );
    } else {
        return (
            <div className="row">
                {resError !== '' ?
                    <div className="alert alert-danger w-50 mx-auto" role="alert">{resError}</div>
                    : <div className="alert alert-warning w-50 mx-auto" role="alert">authorization required</div>}

            </div>
        );
    }
}

export default Location;