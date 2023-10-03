/* eslint-disable */
import React from "react";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import InputText from "../utils/style/InputText";
import Button from "../utils/style/Button";

const HotelCreateForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const createHandler = (event) => {
        event.preventDefault();
        const newElement = {
            hotelName: event.target.hotelNameInput.value,
            hotelDescription: event.target.hotelDescriptionInput.value,
            hotelCity: event.target.hotelCityInput.value
        }
        console.log(newElement);
        HotelService
            .createHotel(token, newElement)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    document.getElementById('hotelCreateFormId').reset();
                    changeStatusHandler({message: 'Hotel created', type: 'success'});
                } else {
                    changeStatusHandler({message: 'Unknown error. Try again', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }


    return (
        <BoxDiv title="Create new hotel:">
            <form onSubmit={createHandler} id="hotelCreateFormId">
                <InputText name='hotelNameInput' description="Hotel name" />
                <InputText name='hotelDescriptionInput' description="Hotel description" />
                <InputText name='hotelCityInput' description="Hotel city" />
                <Button>Create hotel</Button>
            </form>
        </BoxDiv>
    );
}

export default HotelCreateForm;