/* eslint-disable */
import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import Button from "../utils/style/Button";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";


const LocationEditForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [locations, setLocations] = useState([]);
    const [selectedLocation, setSelectedLocation] = useState({
        locationName: '',
        locationDescription: ''
    });


    useEffect(() => {
        if (token !== '') {
            LocationService
                .getAllLocations(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setLocations(result);
                    } else {
                        changeStatusHandler({message: 'Unknown data format', type: 'error'})
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);



    const selectLocationHandler = (event) => {
        let hotel = locations.filter(el => el.id.toString() === event.target.value)[0];
        if (hotel !== undefined) {
            setSelectedLocation(hotel);
        }
    }

    const updateLocationHandler = (event) => {
        event.preventDefault();
        const updateLocationDto = {
            locationName: selectedLocation.locationName,
            locationDescription: selectedLocation.locationDescription
        };
        LocationService
            .updateLocationInfo(token, selectedLocation.id, updateLocationDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Location successfully updated!', type: 'success'})
                    setLocations(locations.map(el => (el.id === result.id) ? result : el))
                } else {
                    changeStatusHandler({message: 'Unknown tesult format!', type: 'error'})
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }


    return (
        <BoxDiv title="Edit hotel info:">
            <div className="row">
                <div className="col-3">
                    <select className="form-select" size="5" aria-label="All accounts list" onChange={selectLocationHandler}>
                        {locations.map(el => <option value={el.id} key={el.id}>{el.locationName}</option>)}
                    </select>
                </div>
                <div className="col-9">
                    <form onSubmit={updateLocationHandler}>
                        <InputTextWithSpan type="text" name="locationName" spanLabel="Location name"
                                   value={selectedLocation.locationName}
                                   onChange={e => setSelectedLocation({...selectedLocation, locationName: e.target.value})}
                        />
                        <InputTextWithSpan type="text" name="locationDescription" spanLabel="Location description"
                                           value={selectedLocation.locationDescription}
                                           onChange={e => setSelectedLocation({...selectedLocation, locationDescription: e.target.value})}
                        />
                        <Button>Update hotel info</Button>
                    </form>

                </div>
            </div>
        </BoxDiv>

    );
}


export default LocationEditForm;