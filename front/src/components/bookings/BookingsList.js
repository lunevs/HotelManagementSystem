/* eslint-disable */
import React, {useEffect, useState} from "react";
import BookingService from "../../services/BookingService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";

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

    const cancelHandler = (id) => {
        BookingService
            .cancelBooking(token, id)
            .then(result => {
                    setBookings(bookings.filter(el => el.id !== id));
                    changeStatusHandler({message: 'booking deleted', type: 'success'});
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }

    return (
        <BoxDiv>
            <table className="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">Room</th>
                        <th scope="col">User</th>
                        <th scope="col">Status</th>
                        <th scope="col">StartDate</th>
                        <th scope="col">EndDate</th>
                        <th scope="col">Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    {bookings.map(el =>
                        <tr key={el.id}>
                            <td>{el.bookedRoom.roomName}</td>
                            <td>{el.accountName}</td>
                            <td>{el.bookingStatus}</td>
                            <td>{new Date(el.startDate).toDateString()}</td>
                            <td>{new Date(el.endDate).toDateString()}</td>
                            <td><button className="btn btn-sm btn-outline-secondary" onClick={() => cancelHandler(el.id)}>Cancel booking</button></td>
                        </tr>)}
                </tbody>
            </table>
        </BoxDiv>
    );
}

export default BookingsList;
