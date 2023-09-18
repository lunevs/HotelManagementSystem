/* eslint-disable */
import React, {useEffect, useState} from "react";
import HotelService from "../../services/HotelService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";

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
        <BoxDiv>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">City</th>
                    <th scope="col">Description</th>
                    <th scope="col">Rooms number</th>
                </tr>
                </thead>
                <tbody>
                {hotels.map(el =>
                    <tr key={el.id}>
                        <td>{el.hotelName}</td>
                        <td>{el.hotelCity}</td>
                        <td>{el.hotelDescription}</td>
                        <td>{el.roomsList.length}</td>
                    </tr>)}
                </tbody>
            </table>

        </BoxDiv>
    );
}

export default HotelsTable;