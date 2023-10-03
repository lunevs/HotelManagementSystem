/* eslint-disable */
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import HotelService from "../../services/HotelService";
import RoomItem from "./RoomItem";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";

const HotelDetails = ({token, changeStatusHandler}) => {

    const hotelId = useParams().id;
    const navigate = useNavigate();
    const [hotel, setHotel] = useState({
        id: 0,
        hotelName: '',
        hotelDescription: '',
        hotelCity: '',
        amenities: [],
        roomsList: []
    });

    useEffect(() => {
        HotelService
            .getHotelDetails(token, hotelId)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setHotel(result);
                } else {
                    changeStatusHandler({message: 'unknown result', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [hotelId])

    return (
        <BoxDiv>
            <div className="row">
                {
                    hotel.roomsList.map(el => <RoomItem key={el.id} token={token} roomElement={el} changeStatusHandler={changeStatusHandler} />)
                }
            </div>
        </BoxDiv>
    );
}

export default HotelDetails;