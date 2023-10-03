import React from "react";
import {useNavigate} from "react-router-dom";

const HotelItem = ({hotelElement}) => {

    const navigate = useNavigate();

    return (
        <div className="card m-3">
        {
            hotelElement === undefined ? <div />
            :
                <div className="row g-0" style={{cursor: "pointer"}} onClick={() => navigate(`/hoteldetails/${hotelElement.id}`)}>
                    <div className="col-md-4">
                        <img src="hotel_default_pict.jpeg" className="img-fluid rounded-start m-4" alt="..." width="200px" />
                    </div>
                    <div className="col-md-8" style={{textAlign: "left"}}>
                        <div className="card-body">
                            <h5 className="card-title">{hotelElement.hotelName}</h5>
                            <p className="card-text">{hotelElement.hotelDescription}</p>
                            <p className="card-text"><small className="text-body-secondary">{hotelElement.hotelCity}</small>
                            </p>
                        </div>
                    </div>
                </div>
        }
        </div>
    );
}

export default HotelItem;