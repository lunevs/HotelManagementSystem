import React, {useEffect, useState} from "react";
import AmenityAddForm from "./AmenityAddForm";
import AmenityListAndUpdate from "./AmenityListAndUpdate";
import amenityService from "../services/AmenityService";

const Amenity = ({token}) => {

    const [resError, setResError] = useState('');
    const [amenities, setAmenities] = useState([]);

    useEffect(() => {
       amenityService
           .getAll(token)
           .then(result => {
               console.log(result)
               setResError('');
               if (Array.isArray(result)) {
                   setAmenities(result);
               }
           })
           .catch(error => {
               if (error.hasOwnProperty('response') && error.response.hasOwnProperty('data') && error.response.data.hasOwnProperty('title')) {
                   setResError(error.response.data.title)
               }
           })
    }, [token]);

    if (token !== '' && resError === '') {
        return (
            <div>
                <div className="row">
                    <div className="col-6">
                        <AmenityAddForm token={token} amenities={amenities} setAmenities={setAmenities} />
                    </div>
                    <div className="col-6">
                        <AmenityListAndUpdate token={token} amenities={amenities} setAmenities={setAmenities} />
                    </div>
                </div>
            </div>
        );
    } else {
        return (
            <div className="row">
                {resError !== '' ?
                    <div className="alert alert-danger w-50 mx-auto" role="alert">{resError}</div>
                    : <div className="alert alert-warning w-50 mx-auto" role="alert">authorization required</div>}

            </div>
        );
    }
}

export default Amenity;

