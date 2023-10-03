/* eslint-disable */
import React, {useEffect, useState} from "react";
import BoxDiv from "../utils/style/BoxDiv";
import {useNavigate} from "react-router-dom";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import amenityService from "../../services/AmenityService";

const RoomDelete = ({token, changeStatusHandler}) => {

    const blankRoom = {
        id: -1,
        roomName: '',
        roomPrice: 0,
        roomCapacity: 0,
        amenities: []
    };

    const blankHotel = {
        id: -1,
        hotelName: '',
        hotelDescription: '',
        hotelCity: '',
        roomsList: []
    };

    const navigate = useNavigate();

    const [hotels, setHotels] = useState([]);
    const [rooms, setRooms] = useState([]);
    const [selectedHotel, setSelectedHotel] = useState(blankHotel);
    const [selectedRoom, setSelectedRoom] = useState(blankRoom);


    useEffect(() => {
        HotelService
            .getAllHotels(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setHotels(result);
                } else {
                    changeStatusHandler({message: 'Unknown data format of Hotels', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }, [token]);

    const selectHotelHandler = (event) => {
        if (event.target.value === "---") {
            setSelectedHotel(blankHotel);
            setRooms([]);
            setSelectedRoom(blankRoom);
        } else {
            let hotel = hotels.filter(el => el.id.toString() === event.target.value)[0];
            if (hotel !== undefined) {
                setSelectedHotel(hotel);
                setSelectedRoom(blankRoom);
                HotelService
                    .getAllRooms(token, hotel.id)
                    .then(result => {
                        if (Array.isArray(result)) {
                            setRooms(result);
                        } else {
                            changeStatusHandler({message: 'Unknown data format of Rooms', type: 'error'});
                        }
                    })
                    .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
            }
        }
    }

    const selectRoomHandler = (event) => {
        if (event.target.value === "---") {
            setSelectedRoom(blankRoom);
        } else {
            let room = rooms.filter(el => el.id.toString() === event.target.value)[0];
            if (room !== undefined) {
                setSelectedRoom(room);
            }
        }
    }

    const deleteHandler = () => {
        const id = selectedRoom.id;
        if (id !== -1) {
            HotelService
                .deleteRoom(token, id)
                .then(result => {
                    setRooms(rooms.filter(el => el.id !== id));
                    changeStatusHandler({message: 'Room was deleted', type: 'success'});
                    setSelectedRoom(blankRoom);
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
        }
    }

    return (
        <BoxDiv title="Delete hotel:">
            <div className="row">
                <div className="col-12 mb-3">
                    <div className="input-group">
                        <span id="selectHotelsForAddRoomSpan" className="input-group-text">Select Hotel:</span>
                        <select className="form-select" size="1" aria-label="All hotels list"
                                id="selectHotelsForAddRoomSpan"
                                name="hotelId"
                                onChange={selectHotelHandler}
                        >
                            <option value="---" key="-1">---</option>
                            {hotels.map(el => <option value={el.id} key={el.id}>{el.hotelName}</option>)}
                        </select>
                    </div>
                </div>
                <div className="col-12 mb-3">
                    <div className="input-group">
                        <span id="selectRoomForEditInfoSpan" className="input-group-text">Select room:</span>
                        <select className="form-select" size="1" aria-label="All rooms list"
                                id="selectRoomForEditInfo"
                                name="roomIdId"
                                onChange={selectRoomHandler}
                        >
                            <option value="---" key="-1">---</option>
                            {rooms.map(el => <option value={el.id} key={el.id}>{el.roomName}</option>)}
                        </select>
                    </div>
                </div>
                <div className="col-12 mb-3">
                    {selectedRoom.id !== -1 ?
                        <div className="alert alert-danger mb-3" role="alert">
                            This action will destroy all data associated with Room {selectedRoom.roomName} (bookings)! Are you sure?
                        </div>
                        : <div />
                    }
                    <button className="btn btn-outline-danger mb-3" onClick={() => deleteHandler()}>Delete room</button>
                </div>
            </div>
        </BoxDiv>
    );
}

export default RoomDelete;