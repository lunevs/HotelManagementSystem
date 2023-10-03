import React from "react";
import BoxDiv from "../utils/style/BoxDiv";
import RoomLineItem from "./RoomLineItem";
import ImagesCarousel from "../utils/ImagesCarousel";

const SearchResultItem = ({hotelElement, token, changeStatusHandler}) => {

    const images = [
        {
            id: 0,
            url: 'images/1_1.jpeg',
            alt: 'seaside hotel 1'
        },{
            id: 1,
            url: 'images/1_2.jpeg',
            alt: 'seaside hotel 2'
        }
    ];

    return (
        <BoxDiv>
            {
                hotelElement === undefined ? <div />
                    :
                    <div className="row g-0">
                        <div className="col-md-4">
                            <div className="card m-2" >
                                <ImagesCarousel images={images} hotelId={hotelElement.hotelId} />
                                <div className="card-body">
                                    <h5 className="card-title">{hotelElement.hotelName}</h5>
                                    <h6 className="card-subtitle mb-2 text-body-secondary">{hotelElement.hotelCity}</h6>
                                    <p className="card-text">{hotelElement.hotelDescription}</p>
                                </div>
                                <ul className="list-group list-group-flush">
                                    {hotelElement.hotelAmenities.map(el => <li className="list-group-item" key={el.id}>{el.amenityName}</li>)}
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