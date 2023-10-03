/* eslint-disable */
import React, {useEffect, useState} from "react";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import Button from "../utils/style/Button";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";


const HotelEditForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [hotels, setHotels] = useState([]);
    const [selectedHotel, setSelectedHotel] = useState({
        id: 0,
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
        let hotel = hotels.filter(el => el.id.toString() === event.target.value)[0];
        if (hotel !== undefined) {
            setSelectedHotel(hotel);
        }
    }

    const updateHotelHandler = (event) => {
        event.preventDefault();
        const updateHotelDto = {
            hotelName: selectedHotel.hotelName,
            hotelDescription: selectedHotel.hotelDescription,
            hotelCity: selectedHotel.hotelCity
        };
        HotelService
            .updateHotelInfo(token, selectedHotel.id, updateHotelDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Hotel successfully updated!', type: 'success'})
                    setHotels(hotels.map(el => (el.id === result.id) ? result : el))
                } else {
                    changeStatusHandler({message: 'Unknown tesult format!', type: 'error'})
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }


    return (
        <BoxDiv title="Edit hotel info:">
            <div className="row">
                <div className="col-3">
                    <select className="form-select" size="5" aria-label="All accounts list" onChange={selectHotelHandler}>
                        {hotels.map(el => <option value={el.id} key={el.id}>{el.hotelName}</option>)}
                    </select>
                </div>
                <div className="col-9">
                    <form onSubmit={updateHotelHandler}>
                        <InputTextWithSpan type="text" name="hotelName" spanLabel="Hotel name"
                                   value={selectedHotel.hotelName}
                                   onChange={e => setSelectedHotel({...selectedHotel, hotelName: e.target.value})}
                        />
                        <InputTextWithSpan type="text" name="hotelDescription" spanLabel="Hotel description"
                                           value={selectedHotel.hotelDescription}
                                           onChange={e => setSelectedHotel({...selectedHotel, hotelDescription: e.target.value})}
                        />
                        <InputTextWithSpan type="text" name="hotelCity" spanLabel="Hotel description"
                                           value={selectedHotel.hotelCity}
                                           onChange={e => setSelectedHotel({...selectedHotel, hotelCity: e.target.value})}
                        />
                        <Button>Update hotel info</Button>
                    </form>

                </div>
            </div>
        </BoxDiv>

    );
}


export default HotelEditForm;