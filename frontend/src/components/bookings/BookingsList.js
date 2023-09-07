/* eslint-disable */
import React, {useEffect, useState} from "react";
import BookingService from "../../services/BookingService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";

const BookingsList = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();
    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        BookingService
            .getAll(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setBookings(result);
                } else {
                    changeStatusHandler({message: 'unknown result data', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [])

    return (
        <div>
            {bookings.map(el => <p>{el.bookedRoom.roomName} - {el.startDate} - {el.endDate}</p>)}
        </div>
    );
}

export default BookingsList;
