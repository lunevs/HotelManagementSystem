import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import locationService from "../../services/LocationService";
import RoomItem from "./RoomItem";

const LocationDetails = ({token, changeStatusHandler}) => {

    const locationId = useParams().id;
    const navigate = useNavigate();
    const [location, setLocation] = useState({
        id: 0,
        locationName: '',
        locationDescription: '',
        amenities: [],
        roomsList: []
    });

    useEffect(() => {
        locationService
            .getLocationDetails(token, locationId)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setLocation(result);
                } else {
                    changeStatusHandler({message: 'unknown result', type: 'error'});
                }
            })
            .catch(error => {
                if (error.hasOwnProperty('response') && error.response.hasOwnProperty('data')) {
                    changeStatusHandler({message: error.response.data.title, type: 'error'});
                    if (error.response.data.title.includes('token')) {
                        navigate('/logout');
                    }
                } else {
                    changeStatusHandler({message: JSON.stringify(error), type: 'error'});
                }
            })
    }, [locationId])

    return (
        <div className="row border border-success-subtle mt-4 mx-2 pb-2 rounded-2 shadow-sm">
            <div className="row">
                {
                    location.roomsList.map(el => <RoomItem token={token} roomElement={el} />)
                }
            </div>
        </div>
    );
}

export default LocationDetails;