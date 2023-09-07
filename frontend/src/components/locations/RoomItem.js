import React, {useState} from "react";
import bookingService from "../../services/BookingService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";

const RoomItem = ({token, roomElement, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [from, setFrom] = useState(new Date().toISOString().split('T')[0]);
    const [to, setTo] = useState(new Date().toISOString().split('T')[0])

    const bookRoomHandler = (e) => {
        e.preventDefault();
        const bookDto = {
            roomId: roomElement.id,
            locationId: roomElement.locationId,
            startDate: from,
            endDate: to,
            numberOfGuests: roomElement.roomCapacity
        }
        bookingService
            .bookRoom(token, bookDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Successfully added!', type: 'success'});
                } else {
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
                        <div className="col-md-4">
                            <img src="room_default_photo.jpeg" className="img-fluid rounded-start m-4" alt="..." width="200px" />
                        </div>
                        <div className="col-md-8" style={{textAlign: "left"}}>
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
                                <div className="border border-light rounded-2 shadow-sm p-3 m-2" >
                                    <form onSubmit={bookRoomHandler}>
                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="inputGroupSelect04">Persons</label>
                                            <select className="form-select" size="1" id="inputGroupSelect04">
                                                {
                                                    Array.from({length: roomElement.roomCapacity}, (v, i) => 1 + i).map(el => <option value={el} key={el}>{el}</option>)
                                                }
                                            </select>
                                        </div>

                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="inputGroupSelect02">From date</label>
                                            <input type="date"
                                                   className="form-control"
                                                   id="inputGroupSelect02"
                                                   value={from}
                                                   onChange={e => setFrom(e.target.value)}
                                            />
                                        </div>

                                        <div className="input-group mb-3">
                                            <label className="input-group-text" htmlFor="inputGroupSelect03">To date</label>
                                            <input type="date"
                                                   className="form-control"
                                                   id="inputGroupSelect03"
                                                   value={to}
                                                   onChange={e => setTo(e.target.value)}
                                            />
                                        </div>

                                        <button type="submit" className="btn btn-outline-success">
                                            Book the room for {(Date.parse(to) - Date.parse(from))/(1000*60*60*24)*roomElement.roomPrice}$
                                        </button>

                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
            }
        </div>
    );
}

export default RoomItem;