import React, {useEffect, useState} from "react";
import BookingService from "../../services/BookingService";

const BookingsList = ({token}) => {

    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        BookingService
            .getAll(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setBookings(result);
                }
            })
            .catch(error => {

            })
    })

    return (
        <div>
            {bookings.map(el => <p>{el.bookedRoom.roomName} - {el.startDate} - {el.endDate}</p>)}
        </div>
    );
}

export default BookingsList;
