/* eslint-disable */
import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import LocationItem from "./LocationItem";

const LocationsTable = ({token, changeStatusHandler}) => {

    const [locations, setLocations] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        if (token !== '') {
            LocationService
                .getAllLocations(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setLocations(result);
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

    return (
        <div className="row">
            {locations.map(el => <LocationItem locationElement={el} key={el.id} />)}
        </div>
    );
}

export default LocationsTable;