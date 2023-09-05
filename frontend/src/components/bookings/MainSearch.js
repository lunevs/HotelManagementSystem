import React, {useEffect, useState} from "react";
import LocationService from "../../services/LocationService";
import LocationItem from "../locations/LocationItem";
import {useNavigate} from "react-router-dom";

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
                .catch(error => {
                    if (error.hasOwnProperty('response') && error.response.hasOwnProperty('data')) {
                        changeStatusHandler({message: error.response.data.title, type: 'error'});
                        if (error.response.data.title.includes('token')) {
                            navigate('/logout');
                        }
                    } else {
                        changeStatusHandler({message: JSON.stringify(error), type: 'error'});
                    }
                });
        }
    }, []);


    return (
        <div className="row border border-success-subtle mt-4 mx-2 pb-2 rounded-2 shadow-sm">

            <div className="row">
                <div className="card p-3 m-3">
                    <form>
                        <div className="input-group mb-3">
                            <label className="input-group-text" htmlFor="inputGroupSelect01">City</label>
                            <select className="form-select" id="inputGroupSelect01">
                                <option selected>All cities</option>
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

                        <button type="submit" className="btn btn-outline-success">filter Locations</button>
                    </form>
                </div>
            </div>

            <div className="row">
                {locations.map(el => <LocationItem locationElement={el} />)}
            </div>
        </div>
    );
}

export default MainSearch;