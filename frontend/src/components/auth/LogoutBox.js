import React, {useEffect} from "react";
import {useNavigate} from "react-router-dom";

const LogoutBox = ({changeTokenHandler}) => {

    const navigate = useNavigate();

    useEffect(() => {
        window.localStorage.removeItem('loggedHotelServiceUser')
        changeTokenHandler('');
        navigate('/');
    })

    return (
        <div />
    );
}

export default LogoutBox;