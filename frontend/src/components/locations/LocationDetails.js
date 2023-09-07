/* eslint-disable */
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import locationService from "../../services/LocationService";
import RoomItem from "./RoomItem";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";

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
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [locationId])

    return (
        <BoxDiv>
            <div className="row">
                {
                    location.roomsList.map(el => <RoomItem key={el.id} token={token} roomElement={el} changeStatusHandler={changeStatusHandler} />)
                }
            </div>
        </BoxDiv>
    );
}

export default LocationDetails;