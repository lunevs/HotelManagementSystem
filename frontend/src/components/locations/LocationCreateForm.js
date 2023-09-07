/* eslint-disable */
import React from "react";
import LocationService from "../../services/LocationService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import InputText from "../utils/style/InputText";
import Button from "../utils/style/Button";

const LocationCreateForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

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
                    document.getElementById('locationCreateFormId').reset();
                    changeStatusHandler({message: 'Location created', type: 'success'});
                } else {
                    changeStatusHandler({message: 'Unknown error. Try again', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }


    return (
        <BoxDiv title="Create new hotel:">
            <form onSubmit={createHandler} id="locationCreateFormId">
                <InputText name="locationNameInput" description="Location name" />
                <InputText name="locationDescriptionInput" description="Location description" />
                <Button>Create hotel</Button>
            </form>
        </BoxDiv>
    );
}

export default LocationCreateForm;