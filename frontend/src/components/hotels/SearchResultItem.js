import React from "react";
import BoxDiv from "../utils/style/BoxDiv";
import RoomLineItem from "./RoomLineItem";

const SearchResultItem = ({hotelElement, token, changeStatusHandler}) => {


    return (
        <BoxDiv>
            {
                hotelElement === undefined ? <div />
                    :
                    <div className="row g-0">
                        <div className="col-md-4">
                            <div className="card m-2" >
                                <img src="hotel_default_pict.jpeg" className="img-fluid rounded-start m-4" alt="..." width="200px" />
                                <div className="card-body">
                                    <h5 className="card-title">{hotelElement.hotelName}</h5>
                                    <h6 className="card-subtitle mb-2 text-body-secondary">{hotelElement.hotelCity}</h6>
                                    <p className="card-text">{hotelElement.hotelDescription}</p>
                                </div>
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item">Amenity 1</li>
                                    <li className="list-group-item">Amenity 2</li>
                                    <li className="list-group-item">Amenity 3</li>
                                </ul>
                            </div>
                        </div>
                        <div className="col-md-8" style={{textAlign: "left"}}>
                            {
                                hotelElement.freeRoomsList.map(room => <RoomLineItem roomElement={room}
                                                                                     key={room.id}
                                                                                     token={token}
                                                                                     changeStatusHandler={changeStatusHandler} />)
                            }
                        </div>
                    </div>
            }
        </BoxDiv>

    );
}

export default SearchResultItem;