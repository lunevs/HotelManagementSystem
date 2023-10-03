/* eslint-disable */
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";
import Button from "../utils/style/Button";

const HotelDelete = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [hotels, setHotels] = useState([]);
    const [selectedHotel, setSelectedHotel] = useState({
        id: -1,
        hotelName: '',
        hotelDescription: '',
        hotelCity: ''
    });

    useEffect(() => {
        if (token !== '') {
            HotelService
                .getAllHotels(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setHotels(result);
                    } else {
                        changeStatusHandler({message: 'Unknown data format', type: 'error'})
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

    const selectHotelHandler = (event) => {
        if (event.target.value === "...") {
            setSelectedHotel({
                id: -1,
                hotelName: '',
                hotelDescription: '',
                hotelCity: ''
            });
        } else {
            let hotel = hotels.filter(el => el.id.toString() === event.target.value)[0];
            if (hotel !== undefined) {
                setSelectedHotel(hotel);
            }
        }
    }

    const deleteHandler = () => {
        if (selectedHotel.id !== -1) {
            const  id = selectedHotel.id;
            HotelService
                .deleteHotel(token, id)
                .then(result => {
                        setHotels(hotels.filter(el => el.id !== id));
                        changeStatusHandler({message: 'Hotel deleted', type: 'success'})
                        setSelectedHotel({
                            id: -1,
                            hotelName: '',
                            hotelDescription: '',
                            hotelCity: ''
                        });
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }

    return (
        <BoxDiv title="Delete hotel:">
            <div className="row">
                <div className="col">
                    <select className="form-select mb-3" size="1" aria-label="All accounts list" onChange={selectHotelHandler}>
                        <option key={-1} value="...">...</option>
                        {hotels.map(el => <option value={el.id} key={el.id}>{el.hotelName}</option>)}
                    </select>
                    {selectedHotel.id !== -1 ?
                        <div className="alert alert-danger mb-3" role="alert">
                            This action will destroy all data associated with Hotel {selectedHotel.hotelName} (rooms, bookings)! Are you sure?
                        </div>
                        : <div />
                    }
                    <button className="btn btn-outline-danger mb-3" onClick={() => deleteHandler()}>Delete hotel</button>
                </div>
            </div>
        </BoxDiv>
    );
}

export default HotelDelete;