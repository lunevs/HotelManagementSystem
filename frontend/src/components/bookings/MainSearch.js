/* eslint-disable */
import React, {useEffect, useState} from "react";
import HotelService from "../../services/HotelService";
import HotelItem from "../hotels/HotelItem";
import {useNavigate} from "react-router-dom";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";
import Button from "../utils/style/Button";
import hotelService from "../../services/HotelService";

const MainSearch = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [hotels, setHotels] = useState([]);
    const [cities, setCities] = useState([]);

    useEffect(() => {
        HotelService
            .getHotelsCities(token)
            .then(result => {
                if (Array.isArray(result)) {
                    setCities(result.sort())
                } else {
                    changeStatusHandler({message: 'unknown result data', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }, [hotels])

    const searchFilterHandler = (event) => {
        event.preventDefault();
        const filterDto = {
            searchCity: event.target.filterCityName.value,
            startDate: event.target.filterFromDate.value,
            endDate: event.target.filterToDate.value,
            neededCapacity: event.target.filterCapacity.value,
            minPrice: event.target.filterFromPrice.value,
            maxPrice: event.target.filterToPrice.value
        };
        hotelService
            .searchHotels(token, filterDto)
                    .then(result => {
                        if (Array.isArray(result)) {
                            setHotels(result);
                        } else {
                            changeStatusHandler({message: 'unknown result data', type: 'error'});
                        }
                    })
                    .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }

    return (
        <BoxDiv>
            <div className="row">
                <div className="card p-3 m-3">
                    <form onSubmit={searchFilterHandler}>
                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect01">City</label>
                            <select className="form-select" id="inputGroupSelect01" name="filterCityName">
                                <option value="" key="0">All cities</option>
                                {
                                    cities.map(el => <option value={el} key={el}>{el}</option>)
                                }
                            </select>
                        </div>

                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect02">From date</label>
                            <input type="date" className="form-control" id="inputGroupSelect02" name="filterFromDate" />

                            <label className="input-group-text" htmlFor="inputGroupSelect03">To date</label>
                            <input type="date" className="form-control" id="inputGroupSelect03" name="filterToDate" />
                        </div>

                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect04">Room capacity</label>
                            <input type="number" step="1" min="1" max="6" className="form-control me-2" id="inputGroupSelect04" name="filterCapacity" />

                            <label className="input-group-text" htmlFor="inputGroupSelect05">Min Room price</label>
                            <input type="number" className="form-control" id="inputGroupSelect05" name="filterFromPrice" />
                            <span className="input-group-text me-2">$</span>

                            <label className="input-group-text" htmlFor="inputGroupSelect06">Max Room price</label>
                            <input type="number" className="form-control" id="inputGroupSelect06" name="filterToPrice" />
                            <span className="input-group-text">$</span>
                        </div>

                        <Button>find Hotels</Button>
                    </form>
                </div>
            </div>

            <div className="row">
                {hotels.map(el => <HotelItem hotelElement={el} key={el.hotelId} />)}
            </div>
        </BoxDiv>
    );
}

export default MainSearch;