/* eslint-disable */
import React, {useEffect, useState} from "react";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import HotelItem from "./HotelItem";

const HotelsTable = ({token, changeStatusHandler}) => {

    const [hotels, setHotels] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        if (token !== '') {
            HotelService
                .getAllHotels(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setHotels(result);
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

    return (
        <div className="row">
            {hotels.map(el => <HotelItem hotelElement={el} key={el.id} />)}
        </div>
    );
}

export default HotelsTable;