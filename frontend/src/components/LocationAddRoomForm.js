import React, {useState} from "react";
import Status from "./Status";
import LocationService from "../services/LocationService";


const LocationAddRoomForm = ({token, locations, setReload}) => {

    const [status, setStatus] = useState('');

    const addRoomToLocationHandler = (event) => {
        event.preventDefault();
        const addRoomDto = {
            locationId: event.target.locationId.value,
            roomName: event.target.roomName.value,
            roomPrice: event.target.roomPrice.value,
            roomMaxCapacity: event.target.roomMaxCapacity.value
        }
        console.log(addRoomDto);
        LocationService
            .addRoomToLocation(token, addRoomDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setStatus('Successfully added');
                    setTimeout(() => setStatus(''), 2000);
                    document.getElementById('createNewRoomFormId').reset();
                    setReload(true);
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
            <p className="text-start text-secondary">Create new room in a location:</p>
            <Status message={status} />

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
                    <div className="col-12 mb-3">
                        <div className="input-group">
                            <span className="input-group-text" id="newRoomNameSpan">New room name:</span>
                            <input type="text" className="form-control"
                                   id="newRoomName"
                                   placeholder="Room name"
                                   aria-label="Room name"
                                   aria-describedby="addon-wrapping"
                                   name="roomName"
                            />
                        </div>
                    </div>
                    <div className="col-6 mb-3">
                        <div className="input-group">
                            <span className="input-group-text" id="newRoomPriceSpan">New room price:</span>
                            <input type="number" className="form-control"
                                   id="newRoomPrice"
                                   placeholder="Room price"
                                   aria-label="Room price"
                                   aria-describedby="addon-wrapping"
                                   name="roomPrice"
                                   step="0.1"
                                   min="0"
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
                                   name="roomMaxCapacity"
                                   step="1"
                                   min="1"
                                   max="6"
                            />
                        </div>
                    </div>
                </div>
                <button className="btn btn-secondary" type="submit">Add Room to Location</button>
            </form>
        </div>

    );
}

export default LocationAddRoomForm;