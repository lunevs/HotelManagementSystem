import React, {useState} from "react";
import Status from "../utils/Status";
import amenityService from "../../services/AmenityService";

const AmenityAddForm = ({token, amenities, setAmenities}) => {

    const [status, setStatus] = useState('');

    const createHandler = (event) => {
        event.preventDefault();
        const amenity = {
            amenityName: event.target.amenityCreateNameInput.value,
            amenityDescription: event.target.amenityCreateDescriptionInput.value,
            amenityType: event.target.amenityCreateTypeInput.value,
            amenityPrice: event.target.amenityCreatePriceInput.value
        }
        console.log(amenity);
        amenityService
            .createAmenity(token, amenity)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setAmenities([...amenities, result]);
                    setStatus('Successfully added');
                    setTimeout(() => setStatus(''), 2000);
                    document.getElementById('amenityCreateFormId').reset();
                }
            })
            .catch(e => {
                console.log(e)
            })

    }

    return (
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Create new amenity:</p>
            <Status message={status} />
            <form onSubmit={createHandler} id="amenityCreateFormId">
                <div className="input-group mb-1">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Amenity name"
                           aria-label="Amenity name"
                           aria-describedby="addon-wrapping"
                           name="amenityCreateNameInput"
                    />
                </div>
                <div className="input-group mb-2">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Amenity description"
                           aria-label="Amenity description"
                           aria-describedby="addon-wrapping"
                           name="amenityCreateDescriptionInput"
                    />
                </div>
                <div className="input-group mb-2 mx-2">
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio"
                               name="amenityCreateTypeInput"
                               id="amenityCreateTypeInput1"
                               value="ROOM" />
                        <label className="form-check-label" htmlFor="amenityCreateTypeInput1">Room amenity</label>
                    </div>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio"
                               name="amenityCreateTypeInput"
                               id="amenityCreateTypeInput2"
                               value="LOCATION" />
                        <label className="form-check-label" htmlFor="amenityCreateTypeInput2">Location Amenity</label>
                    </div>
                </div>
                <div className="input-group mb-1">
                    <input type="number"
                           className="form-control m-2"
                           placeholder="Amenity price"
                           aria-label="Amenity price"
                           aria-describedby="addon-wrapping"
                           step="0.01"
                           name="amenityCreatePriceInput"
                    />
                </div>
                <button className="btn btn-secondary m-2" type="submit">Create amenity</button>
            </form>
        </div>
    );
}

export default AmenityAddForm;

