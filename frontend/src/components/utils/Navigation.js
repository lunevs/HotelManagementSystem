import React from "react";
import {Link} from "react-router-dom";


const Navigation = ({isAuth}) => {
    return (
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <Link className="navbar-brand" href="/">Houses for rent | Marketplace</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link className="nav-link" to={"/search"}>Find location</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to={"/bookings"}>My bookings</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="/mylocations" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    My locations
                                </a>
                                <ul className="dropdown-menu">
                                    <li><Link className="dropdown-item" to={"/mylocations"}>My locations list</Link></li>
                                    <li>
                                        <hr className="dropdown-divider" />
                                    </li>
                                    <li><Link className="dropdown-item" to={"/addlocation"}>Add location</Link></li>
                                    <li><Link className="dropdown-item" to={"/editlocation"}>Edit location</Link></li>
                                    <li>
                                        <hr className="dropdown-divider" />
                                    </li>
                                    <li><Link className="dropdown-item" to={"/addroom"}>Add room</Link></li>
                                    <li><Link className="dropdown-item" to={"/editroom"}>Edit room</Link></li>
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="/accounts" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    Administration
                                </a>
                                <ul className="dropdown-menu">
                                    <li><Link className="dropdown-item" to={`/accounts`}>Li Accounts</Link></li>
                                    <li><Link className="dropdown-item" to={`/amenities`}>Li Amenities</Link></li>
                                </ul>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                            {
                                isAuth ?
                                    <Link className="btn btn-outline-success" to={"/logout"} type="submit">Logout</Link>
                                    :
                                    <Link className="btn btn-outline-success" to={"/login"} type="submit">Login</Link>
                            }
                        </form>
                    </div>
                </div>
            </nav>
    );
}

export default Navigation;