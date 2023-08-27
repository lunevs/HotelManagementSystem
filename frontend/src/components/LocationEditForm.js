import React, {useState} from "react";
import LocationService from "../services/LocationService";
import Status from "./Status";


const LocationEditForm = ({token, locations, setLocations}) => {

    const [status, setStatus] = useState('');
    const [selectedLocation, setSelectedLocation] = useState({
        locationName: '',
        locationDescription: '',
        roomsList: []
    });



    const selectLocationHandler = (event) => {
        let location = locations.filter(el => el.id.toString() === event.target.value)[0];
        if (location !== undefined) {
            setSelectedLocation(location);
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
                    setStatus("Successfully updated!");
                    setTimeout(() => setStatus(''), 2000);
                    setLocations(locations.map(el => {
                        if (el.id === result.id) {
                            return result;
                        } else {
                            return el;
                        }
                    }))
                } else {
                    console.log("Unknown error: " + result);
                }
            })
            .catch(error => console.log(error));
    }


    return (
        <div className="row border border-success-subtle mt-4 mx-2 pb-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Edit location info:</p>
            <Status message={status} />
            
            <div className="row">
                <div className="col-3">
                    <select className="form-select" size="5" aria-label="All accounts list" onChange={selectLocationHandler}>
                        {locations.map(el => <option value={el.id} key={el.id}>{el.locationName}</option>)}
                    </select>
                </div>
                <div className="col-9">
                    <form>
                        <div className="input-group mb-3">
                            <span className="input-group-text w-25" id="addon-wrapping">Location name</span>
                            <input type="text" className="form-control"
                                   placeholder="Location name"
                                   aria-label="Location name"
                                   aria-describedby="addon-wrapping"
                                   name="locationName"
                                   value={selectedLocation.locationName}
                                   onChange={e => setSelectedLocation({...selectedLocation, locationName: e.target.value})}
                            />
                        </div>

                        <div className="input-group mb-3">
                            <span className="input-group-text w-25" id="addon-wrapping">Location description</span>
                            <input type="text" className="form-control"
                                   placeholder="Location description"
                                   aria-label="Location description"
                                   aria-describedby="addon-wrapping"
                                   name="locationDescription"
                                   value={selectedLocation.locationDescription}
                                   onChange={e => setSelectedLocation({...selectedLocation, locationDescription: e.target.value})}
                            />
                        </div>

                        <button className="btn btn-secondary" type="button" onClick={updateLocationHandler}>Update location info</button>
                    </form>

                </div>
            </div>
        </div>

    );
}


export default LocationEditForm;