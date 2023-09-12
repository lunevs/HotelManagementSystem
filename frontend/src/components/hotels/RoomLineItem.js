import React from "react";
import Button from "../utils/style/Button";
import bookingService from "../../services/BookingService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";

const RoomLineItem = ({token, roomElement, changeStatusHandler}) => {

    const navigate = useNavigate();

    const doBook = (event) => {
        event.preventDefault();
        const bookDto = {
            roomId: roomElement.id,
            hotelId: roomElement.hotelId,
            startDate: event.target.bookFromDate.value,
            endDate: event.target.bookToDate.value,
            numberOfGuests: roomElement.roomCapacity
        }
        bookingService
            .bookRoom(token, bookDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Successfully booked!', type: 'success'});
                } else {
                    console.log("result: " + result)
                    changeStatusHandler({message: 'Unknown result data', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }

    return (
        <div className="card m-3">
            {
                roomElement === undefined ? <div />
                    :
                    <div className="row g-0">
                        <div className="col-6">
                            <img src="room_default_photo.jpeg" className="img-fluid rounded-start mt-2 ms-2" alt="..." width="200px" />
                            <div className="card-body">
                                <h5 className="card-title">{roomElement.roomName}</h5>
                                <table>
                                    <tbody>
                                    <tr>
                                        <td>room capacity:</td>
                                        <td><p className="card-text">{roomElement.roomCapacity}</p></td>
                                    </tr>
                                    <tr>
                                        <td>price for 1 night: </td>
                                        <td><p className="card-text">{roomElement.roomPrice}$</p></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div className="col-6">
                            <div className="m-2">
                                <form onSubmit={doBook}>
                                    <div className="input-group mb-3">
                                        <label className="input-group-text" htmlFor="inputGroupSelect02">From date</label>
                                        <input type="date" className="form-control" id="inputGroupSelect02" name="bookFromDate" />
                                    </div>
                                    <div className="input-group mb-3">
                                        <label className="input-group-text" htmlFor="inputGroupSelect02">To date</label>
                                        <input type="date" className="form-control" id="inputGroupSelect02" name="bookToDate" />
                                    </div>
                                    <div className="input-group mb-3">
                                        <Button>Book this room</Button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
            }
        </div>
    );
}

export default RoomLineItem;