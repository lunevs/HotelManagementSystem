import React, {useState} from "react";
import LocationService from "../../services/LocationService";
import Status from "../utils/Status";

const LocationCreateForm = ({token, locations, setLocations}) => {

    const [status, setStatus] = useState('');

    const createHandler = (event) => {
        event.preventDefault();
        const newElement = {
            locationName: event.target.locationNameInput.value,
            locationDescription: event.target.locationDescriptionInput.value
        }
        console.log(newElement);
        LocationService
            .createLocation(token, newElement)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    const newLocations = [...locations, result];
                    setLocations(newLocations);
                    setStatus("Successfully created");
                    document.getElementById('locationCreateFormId').reset();
                    setTimeout(() => setStatus(''), 2000);

                    console.log(result);
                } else {
                    console.log("unknown error: " + result);
                }
            })
            .catch(error => console.log(error));
    }


    return (
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Create new location:</p>
            <Status message={status} />
            <form onSubmit={createHandler} id="locationCreateFormId">
                <div className="input-group mb-1">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Location name"
                           aria-label="Location name"
                           aria-describedby="addon-wrapping"
                           name="locationNameInput"
                    />
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Location description"
                           aria-label="Location description"
                           aria-describedby="addon-wrapping"
                           name="locationDescriptionInput"
                    />
                    <button className="btn btn-secondary m-2" type="submit">Create location</button>
                </div>
            </form>
        </div>
    );
}

export default LocationCreateForm;