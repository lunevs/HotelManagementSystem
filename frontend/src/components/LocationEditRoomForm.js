import React, {useState} from "react";
import Status from "./Status";
import LocationService from "../services/LocationService";


const LocationEditRoomForm = ({token, locations, setReload}) => {

    const [status, setStatus] = useState('');
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
        roomCapacity: 0
    });

    const selectLocationHandler = (event) => {
        let location = locations.filter(el => el.id.toString() === event.target.value)[0];
        if (location !== undefined) {
            setSelectedLocation(location);
            setSelectedRoom({
                id: 0,
                roomName: '',
                roomPrice: 0,
                roomCapacity: 0
            });
        }
    }

    const selectRoomHandler = (event) => {
        let room = selectedLocation.roomsList.filter(el => el.id.toString() === event.target.value)[0];
        if (room !== undefined) {
            console.log(room);
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
            roomMaxCapacity: selectedRoom.roomCapacity
        }
        console.log(editRoomDto);
        LocationService
            .updateRoom(token, roomId, editRoomDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setReload(true);
                    setStatus('Successfully added');
                    setTimeout(() => setStatus(''), 2000);
                    document.getElementById('editRoomFormId').reset();
                    setSelectedRoom({
                        id: 0,
                        roomName: '',
                        roomPrice: 0,
                        roomCapacity: 0
                    });
                }
            })
            .catch(error => {
                console.log(error);
                setStatus('Error');
                setTimeout(() => setStatus(''), 2000);
            });
    };

    return (
        <div className="row border border-success-subtle mt-4 mx-2 pb-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Edit room information:</p>
            <Status message={status} />

            <form onSubmit={saveRoomHandler} id="editRoomFormId">
                <div className="row">
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span id="selectLocationsForAddRoomSpan" className="input-group-text">Select Location:</span>
                            <select className="form-select" size="1"
                                    aria-label="All locations list"
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
                            <select className="form-select" size="1"
                                    aria-label="All rooms list"
                                    id="selectRoomForEditInfo"
                                    name="roomIdId"
                                    onChange={selectRoomHandler}
                            >
                                <option value="---" key="0">---</option>
                                {selectedLocation.roomsList.map(el => <option value={el.id} key={el.id}>{el.roomName}</option>)}
                            </select>
                        </div>
                    </div>
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span className="input-group-text" id="newRoomNameSpan">Room name:</span>
                            <input type="text" className="form-control"
                                   id="newRoomName"
                                   placeholder="Room name"
                                   aria-label="Room name"
                                   aria-describedby="addon-wrapping"
                                   name="roomName"
                                   value={selectedRoom.roomName}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomName: e.target.value})}
                            />
                        </div>
                    </div>
                    <div className="col-6 mb-3">
                        <div className="input-group">
                            <span className="input-group-text" id="newRoomPriceSpan">Room price:</span>
                            <input type="number" className="form-control"
                                   id="newRoomPrice"
                                   placeholder="Room price"
                                   aria-label="Room price"
                                   aria-describedby="addon-wrapping"
                                   name="roomPrice"
                                   step="0.1"
                                   min="0"
                                   value={selectedRoom.roomPrice}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomPrice: Number.parseFloat(e.target.value)})}
                            />
                        </div>
                    </div>
                    <div className="col-6 mb-3">
                        <div className="input-group">
                            <span className="input-group-text" id="newRoomCapacitySpan">Room maximum capacity:</span>
                            <input type="number" className="form-control"
                                   id="newRoomCapacity"
                                   placeholder="Room capacity"
                                   aria-label="Room capacity"
                                   aria-describedby="addon-wrapping"
                                   name="roomCapacity"
                                   step="1"
                                   min="1"
                                   max="6"
                                   value={selectedRoom.roomCapacity}
                                   onChange={(e) => setSelectedRoom({...selectedRoom, roomCapacity: Number.parseInt(e.target.value)})}
                            />
                        </div>
                    </div>
                </div>
                <button className="btn btn-secondary" type="submit">Save Room</button>
            </form>
        </div>

    );
}

export default LocationEditRoomForm;