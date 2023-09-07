/* eslint-disable */
import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import AmenityElementCheckbox from "../amenities/AmenityElementCheckbox";
import amenityService from "../../services/AmenityService";
import {useNavigate} from "react-router-dom";
import ErrorsHandler from "../utils/Utils";
import locationService from "../../services/LocationService";
import BoxDiv from "../utils/style/BoxDiv";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";
import Button from "../utils/style/Button";


const LocationEditRoomForm = ({token, changeStatusHandler}) => {

    const navigate= useNavigate();

    const [locations, setLocations] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [amenities, setAmenities] = useState([]);
    const [selectedLocation, setSelectedLocation] = useState({
        id: 0,
        locationName: '',
        locationDescription: '',
        roomsList: []
    });
    const [selectedRoom, setSelectedRoom] = useState({
        id: 0,
        roomName: '',
        roomPrice: 0,
        roomCapacity: 0,
        amenities: []
    });

    useEffect(() => {
        locationService
            .getAllLocations(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setLocations(result);
                } else {
                    changeStatusHandler({message: 'Unknown data format of Locations', type: 'error'});
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
                    changeStatusHandler({message: 'Unknown data format of Amenities', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [token]);


    const selectLocationHandler = (event) => {
        let location = locations.filter(el => el.id.toString() === event.target.value)[0];
        if (location !== undefined) {
            setSelectedLocation(location);
            setSelectedRoom({
                id: 0,
                roomName: '',
                roomPrice: 0,
                roomCapacity: 0,
                amenities: []
            });
            LocationService
                .getAllRooms(token, location.id)
                .then(result => {
                    if (Array.isArray(result)) {
                        setRooms(result);
                    } else {
                        changeStatusHandler({message: 'Unknown data format of Rooms', type: 'error'});
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
        }
    }

    const selectRoomHandler = (event) => {
        let room = rooms.filter(el => el.id.toString() === event.target.value)[0];
        if (room !== undefined) {
            setSelectedRoom(room);
        }
    }

    const saveRoomHandler = (event) => {
        event.preventDefault();
        const roomId = selectedRoom.id;
        const editRoomDto = {
            locationId: selectedLocation.id,
            roomName: selectedRoom.roomName,
            roomPrice: selectedRoom.roomPrice,
            roomMaxCapacity: selectedRoom.roomCapacity,
            amenitiesList: selectedRoom.amenities
        }
        LocationService
            .updateRoom(token, roomId, editRoomDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Room successfully updated!', type: 'success'});
                    setSelectedRoom({
                        id: 0,
                        roomName: '',
                        roomPrice: 0,
                        roomCapacity: 0,
                        amenities: []
                    });
                    document.getElementById('editRoomFormId').reset();
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    };

    const changeAmenitySelectionHandler = (event) => {
        if (selectedRoom.id !== 0) {
            const id = event.target.value;
            if (selectedRoom.amenities.some(el => el.id === id)) {
                const newAmenities = selectedRoom.amenities.filter(el => el.id !== id);
                setSelectedRoom({...selectedRoom, amenities: newAmenities});
            } else {
                let newAmenities = amenities.find(el => el.id.toString() === id);
                setSelectedRoom({...selectedRoom, amenities: [...selectedRoom.amenities, newAmenities]});
            }
        }
    }

    return (
        <BoxDiv title="Edit room information:">
            <form onSubmit={saveRoomHandler} id="editRoomFormId">
                <div className="row">
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span id="selectLocationsForAddRoomSpan" className="input-group-text">Select Location:</span>
                            <select className="form-select" size="1" aria-label="All locations list"
                                    id="selectLocationsForAddRoom"
                                    name="locationId"
                                    onChange={selectLocationHandler}
                            >
                                <option value="---" key="0">---</option>
                                {locations.map(el => <option value={el.id} key={el.id}>{el.locationName}</option>)}
                            </select>
                        </div>
                    </div>
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span id="selectRoomForEditInfoSpan" className="input-group-text">Select room:</span>
                            <select className="form-select" size="1" aria-label="All rooms list"
                                    id="selectRoomForEditInfo"
                                    name="roomIdId"
                                    onChange={selectRoomHandler}
                            >
                                <option value="---" key="0">---</option>
                                {rooms.map(el => <option value={el.id} key={el.id}>{el.roomName}</option>)}
                            </select>
                        </div>
                    </div>
                    <div className="col-12">
                        <InputTextWithSpan type="text" name="roomName" spanLabel="Room name:"
                                   value={selectedRoom.roomName}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomName: e.target.value})}
                        />
                    </div>
                    <div className="col-6">
                        <InputTextWithSpan type="number" name="roomPrice" spanLabel="Room price:" money={true}
                                   value={selectedRoom.roomPrice}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomPrice: Number.parseFloat(e.target.value)})}
                        />
                    </div>
                    <div className="col-6">
                        <InputTextWithSpan type="number" name="roomCapacity" spanLabel="Room capacity:"
                                   value={selectedRoom.roomCapacity}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomCapacity: Number.parseInt(e.target.value)})}
                        />
                    </div>
                    <div className="col-12 mb-3 g-3">
                        <div className="row gy-2 gx-3">
                            {amenities.filter(el => el.amenityType === "ROOM").map(el =>
                                <AmenityElementCheckbox
                                    key={el.id}
                                    el_id={el.id}
                                    el_name={el.amenityName}
                                    el_price={el.amenityPrice}
                                    checked={selectedRoom.amenities.some(el2 => el2.id === el.id)}
                                    onChange={changeAmenitySelectionHandler}
                                /> )}
                        </div>
                    </div>
                </div>
                <Button>Save Room</Button>
            </form>
        </BoxDiv>

    );
}

export default LocationEditRoomForm;