/* eslint-disable */
import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import amenityService from "../../services/AmenityService";
import AmenityElementCheckbox from "../amenities/AmenityElementCheckbox";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import locationService from "../../services/LocationService";
import BoxDiv from "../utils/style/BoxDiv";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";
import Button from "../utils/style/Button";


const LocationAddRoomForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [locations, setLocations] = useState([]);
    const [amenities, setAmenities] = useState([]);

    useEffect(() => {
        locationService
            .getAllLocations(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setLocations(result);
                } else {
                    changeStatusHandler({message: 'Unknown data format for Locations', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [token]);

    useEffect(() => {
        amenityService
            .getAll(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setAmenities(result);
                } else {
                    changeStatusHandler({message: 'Unknown data format for Amenities', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [token]);

    const addRoomToLocationHandler = (event) => {
        event.preventDefault();
        let checkedIds = [];
        event.target.amenitiesList.forEach(el => {
            let id = el.value;
            if (el.checked) {
                checkedIds.push(id)
            }
        })
        const checkedAmenities = amenities.filter(el => checkedIds.includes(el.id.toString()));
        const addRoomDto = {
            locationId: event.target.locationId.value,
            roomName: event.target.roomName.value,
            roomPrice: event.target.roomPrice.value,
            roomMaxCapacity: event.target.roomMaxCapacity.value,
            amenitiesList: checkedAmenities
        }
        LocationService
            .addRoomToLocation(token, addRoomDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Room successfully added', type: 'success'});
                    document.getElementById('createNewRoomFormId').reset();
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    };

    return (
        <BoxDiv>
            <p className="text-start text-secondary">Create new room in a location:</p>

            <form onSubmit={addRoomToLocationHandler} id="createNewRoomFormId">
                <div className="row">
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span id="selectLocationsForAddRoomSpan" className="input-group-text">Select Location for a Room:</span>
                            <select className="form-select" size="1"
                                    aria-label="All locations list"
                                    id="selectLocationsForAddRoom"
                                    name="locationId"
                            >
                                <option value="---" key="0">---</option>
                                {locations.map(el => <option value={el.id} key={el.id}>{el.locationName}</option>)}
                            </select>
                        </div>
                    </div>
                    <div className="col-12">
                        <InputTextWithSpan type="text" name="roomName" spanLabel="New room name:" />
                    </div>
                    <div className="col-6">
                        <InputTextWithSpan type="number" name="roomPrice" spanLabel="New room price:" money={true}/>
                    </div>
                    <div className="col-6">
                        <InputTextWithSpan type="number" name="roomMaxCapacity" spanLabel="Room maximum capacity:" />
                    </div>
                    <div className="col-12 mb-3 g-3">
                        <div className="row gy-2 gx-3">
                            {amenities.filter(el => el.amenityType === "ROOM").map(el =>
                                <AmenityElementCheckbox key={el.id} el_id={el.id} el_name={el.amenityName} el_price={el.amenityPrice} /> )}
                        </div>
                    </div>
                </div>
                <Button>Add Room to Location</Button>
            </form>
        </BoxDiv>

    );
}

export default LocationAddRoomForm;