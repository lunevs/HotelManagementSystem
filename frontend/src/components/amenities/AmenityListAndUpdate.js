/* eslint-disable */
import React, {useEffect, useState} from "react";
import amenityService from "../../services/AmenityService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import InputText from "../utils/style/InputText";
import InputCheckBox from "../utils/style/InputCheckBox";
import InputNumber from "../utils/style/InputNumber";
import Button from "../utils/style/Button";


const AmenityListAndUpdate = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [amenities, setAmenities] = useState([]);
    const [selectedAmenity, setSelectedAmenity] = useState({
        id: 0,
        amenityName: '',
        amenityDescription: '',
        amenityType: '',
        amenityPrice: 0
    })

    useEffect(() => {
        amenityService
            .getAll(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setAmenities(result);
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [token]);

    const updateHandler = (event) => {
        event.preventDefault();
        amenityService
            .updateAmenity(token, selectedAmenity)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    const newAmenities = amenities.map(el => (el.id === result.id) ? result : el)
                    setAmenities(newAmenities);
                    changeStatusHandler({message: 'Amenity successfully updated', type: 'success'})
                }
            })
            .catch(e => ErrorsHandler(e, changeStatusHandler, navigate))
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
        <BoxDiv>
            <p className="text-start text-secondary">All amenities list:</p>
            <div className="input-group mb-1">
                <select className="form-select" size="1" aria-label="All amenities list" onChange={selectHandler}>
                    <option value="---" key="0">---</option>
                    {amenities.map(el => <option value={el.id} key={el.id}>{el.amenityName}</option>)}
                </select>
            </div>
            <form id="amenitiesListViewFormId" onSubmit={updateHandler}>
                <InputText name="amenityUpdateNameInput" description="Amenity name"
                           value={selectedAmenity.amenityName}
                           onChange={e => setSelectedAmenity({...selectedAmenity, amenityName: e.target.value})}
                />
                <InputText name="amenityUpdateDescriptionInput" description="Amenity description"
                           value={selectedAmenity.amenityDescription}
                           onChange={e => setSelectedAmenity({...selectedAmenity, amenityName: e.target.value})}
                />
                <div className="input-group mb-2 mx-2">
                    <InputCheckBox id="amenityUpdateTypeInput1" name="amenityUpdateTypeInput" value="ROOM"
                                   checked={selectedAmenity.amenityType === "ROOM"}
                                   onChange={e => setSelectedAmenity({...selectedAmenity, amenityType: e.target.value})}>
                        Room amenity
                    </InputCheckBox>
                    <InputCheckBox id="amenityUpdateTypeInput2" name="amenityUpdateTypeInput" value="HOTEL"
                                   checked={selectedAmenity.amenityType === "HOTEL"}
                                   onChange={e => setSelectedAmenity({...selectedAmenity, amenityType: e.target.value})}>
                        Room amenity
                    </InputCheckBox>
                </div>
                <InputNumber name="amenityUpdatePriceInput" description="Amenity price"
                             value={selectedAmenity.amenityPrice}
                             onChange={e => setSelectedAmenity({...selectedAmenity, amenityPrice: Number.parseFloat(e.target.value)})} />
                <Button>Update amenity</Button>
            </form>
        </BoxDiv>
    );
}

export default AmenityListAndUpdate;

