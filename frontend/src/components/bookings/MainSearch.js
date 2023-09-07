/* eslint-disable */
import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import LocationItem from "../locations/LocationItem";
import {useNavigate} from "react-router-dom";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";
import Button from "../utils/style/Button";

const MainSearch = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [locations, setLocations] = useState([]);

    useEffect(() => {
        if (token !== '') {
            LocationService
                .getAllLocations(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setLocations(result);
                    } else {
                        changeStatusHandler({message: 'unknown result data', type: 'error'});
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, []);


    return (
        <BoxDiv>
            <div className="row">
                <div className="card p-3 m-3">
                    <form>
                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect01">City</label>
                            <select className="form-select" id="inputGroupSelect01">
                                <option value="0">All cities</option>
                                <option value="1">London</option>
                                <option value="2">Paris</option>
                                <option value="3">Berlin</option>
                            </select>
                        </div>

                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect02">From date</label>
                            <input type="date" className="form-control" id="inputGroupSelect02" />

                            <label className="input-group-text" htmlFor="inputGroupSelect03">To date</label>
                            <input type="date" className="form-control" id="inputGroupSelect03" />
                        </div>

                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect04">Room capacity</label>
                            <input type="number" step="1" min="1" max="6" className="form-control" id="inputGroupSelect04" />

                            <label className="input-group-text" htmlFor="inputGroupSelect05">Min price</label>
                            <input type="number" className="form-control" id="inputGroupSelect05" />

                            <label className="input-group-text" htmlFor="inputGroupSelect06">Max price</label>
                            <input type="number" className="form-control" id="inputGroupSelect06" />
                        </div>

                        <Button>filter Locations</Button>
                    </form>
                </div>
            </div>

            <div className="row">
                {locations.map(el => <LocationItem locationElement={el} key={el.id} />)}
            </div>
        </BoxDiv>
    );
}

export default MainSearch;