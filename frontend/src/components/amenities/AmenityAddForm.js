import React from "react";
import amenityService from "../../services/AmenityService";
import {useNavigate} from "react-router-dom";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";
import InputText from "../utils/style/InputText";
import InputNumber from "../utils/style/InputNumber";
import InputCheckBox from "../utils/style/InputCheckBox";
import Button from "../utils/style/Button";

const AmenityAddForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const createHandler = (event) => {
        event.preventDefault();
        const amenity = {
            amenityName: event.target.amenityCreateNameInput.value,
            amenityDescription: event.target.amenityCreateDescriptionInput.value,
            amenityType: event.target.amenityCreateTypeInput.value,
            amenityPrice: event.target.amenityCreatePriceInput.value
        }
        amenityService
            .createAmenity(token, amenity)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Amenity successfully added', type: 'success'})
                    document.getElementById('amenityCreateFormId').reset();
                } else {
                    changeStatusHandler({message: 'Unknown error', type: 'error'})
                }
            })
            .catch(e => ErrorsHandler(e, changeStatusHandler, navigate))

    }

    return (
        <BoxDiv>
            <p className="text-start text-secondary">Create new amenity:</p>
            <form onSubmit={createHandler} id="amenityCreateFormId">
                <InputText name="amenityCreateNameInput" description="Amenity name" />
                <InputText name="amenityCreateDescriptionInput" description="Amenity description" />
                <div className="input-group mb-2 mx-2">
                    <InputCheckBox id="amenityCreateTypeInput1" name="amenityCreateTypeInput" value="ROOM">
                        Room amenity
                    </InputCheckBox>
                    <InputCheckBox id="amenityCreateTypeInput2" name="amenityCreateTypeInput" value="HOTEL">
                        Location amenity
                    </InputCheckBox>
                </div>
                <InputNumber name="amenityCreatePriceInput" description="Amenity price" />

                <Button>Create amenity</Button>
            </form>
        </BoxDiv>
    );
}

export default AmenityAddForm;

