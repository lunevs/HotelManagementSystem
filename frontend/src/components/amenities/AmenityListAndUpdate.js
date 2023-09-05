import React, {useState} from "react";
import Status from "../utils/Status";
import amenityService from "../../services/AmenityService";


const AmenityListAndUpdate = ({token, amenities, setAmenities}) => {

    const [status, setStatus] = useState('');
    const [selectedAmenity, setSelectedAmenity] = useState({
        id: 0,
        amenityName: '',
        amenityDescription: '',
        amenityType: '',
        amenityPrice: 0
    })

    const updateHandler = (event) => {
        event.preventDefault();
        amenityService
            .updateAmenity(token, selectedAmenity)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    const newAmenities = amenities.map(el => (el.id === result.id) ? result : el)
                    console.log(newAmenities);
                    setAmenities(newAmenities);
                    setStatus('Successfully updated');
                    setTimeout(() => setStatus(''), 2000);
                }
            })
            .catch(e => {
                console.log(e)
            })
    }

    const selectHandler = (event) => {
        if (event.currentTarget.value === "---") {
            setSelectedAmenity({
                id: 0,
                amenityName: '',
                amenityDescription: '',
                amenityType: '',
                amenityPrice: 0
            });
        } else {
            const selectedAmenity = amenities.filter(el => el.id.toString() === event.currentTarget.value)[0];
            if (selectedAmenity !== undefined) {
                setSelectedAmenity(selectedAmenity);
            }
        }
    }

    return (
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">All amenities list:</p>
            <Status message={status} />
            <div className="input-group mb-1">
                <select className="form-select" size="1" aria-label="All amenities list" onChange={selectHandler}>
                    <option value="---" key="0">---</option>
                    {amenities.map(el => <option value={el.id} key={el.id}>{el.amenityName}</option>)}
                </select>
            </div>
            <form id="amenitiesListViewFormId" onSubmit={updateHandler}>
                <div className="input-group mb-1">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Amenity name"
                           aria-label="Amenity name"
                           aria-describedby="addon-wrapping"
                           name="amenityUpdateNameInput"
                           value={selectedAmenity.amenityName}
                           onChange={e => setSelectedAmenity({...selectedAmenity, amenityName: e.target.value})}
                    />
                </div>
                <div className="input-group mb-1">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Amenity description"
                           aria-label="Amenity description"
                           aria-describedby="addon-wrapping"
                           name="amenityUpdateDescriptionInput"
                           value={selectedAmenity.amenityDescription}
                           onChange={e => setSelectedAmenity({...selectedAmenity, amenityDescription: e.target.value})}
                    />
                </div>
                <div className="input-group mb-2 mx-2">
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio"
                               name="amenityUpdateTypeInput"
                               id="amenityUpdateTypeInput1"
                               value="ROOM"
                               checked={selectedAmenity.amenityType === "ROOM"}
                               onChange={e => setSelectedAmenity({...selectedAmenity, amenityType: e.target.value})}
                        />
                        <label className="form-check-label" htmlFor="amenityUpdateTypeInput1">Room amenity</label>
                    </div>
                    <div className="form-check form-check-inline">
                        <input className="form-check-input" type="radio"
                               name="amenityUpdateTypeInput"
                               id="amenityUpdateTypeInput2"
                               value="LOCATION"
                               checked={selectedAmenity.amenityType === "LOCATION"}
                               onChange={e => setSelectedAmenity({...selectedAmenity, amenityType: e.target.value})}
                        />
                        <label className="form-check-label" htmlFor="amenityUpdateTypeInput2">Location Amenity</label>
                    </div>
                </div>
                <div className="input-group mb-1">
                    <input type="number"
                           className="form-control m-2"
                           placeholder="Amenity price"
                           aria-label="Amenity price"
                           aria-describedby="addon-wrapping"
                           name="amenityUpdatePriceInput"
                           step="0.01"
                           value={selectedAmenity.amenityPrice}
                           onChange={e => setSelectedAmenity({...selectedAmenity, amenityPrice: Number.parseFloat(e.target.value)})}
                    />
                </div>
                <button className="btn btn-secondary m-2" type="submit">Update amenity</button>
            </form>
        </div>
    );
}

export default AmenityListAndUpdate;

