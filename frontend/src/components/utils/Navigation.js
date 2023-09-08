import React from "react";
import {Link} from "react-router-dom";


const Navigation = ({isAuth}) => {
    return (
            <nav className="navbar navbar-expand-lg bg-body-tertiary">
                <div className="container-fluid">
                    <Link className="navbar-brand" to={"/"}>Houses for rent | Marketplace</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                            <li className="nav-item">
                                <Link className="nav-link" to={"/search"}>Find Hotel</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to={"/bookings"}>My bookings</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <a className="nav-link dropdown-toggle" href="/myhotels" role="button" data-bs-toggle="dropdown"
                                   aria-expanded="false">
                                    My Hotels
                                </a>
                                <ul className="dropdown-menu">
                                    <li><Link className="dropdown-item" to={"/myhotels"}>My Hotels list</Link></li>
                                    <li>
                                        <hr className="dropdown-divider" />
                                    </li>
                                    <li><Link className="dropdown-item" to={"/addhotel"}>Add Hotel</Link></li>
                                    <li><Link className="dropdown-item" to={"/edithotel"}>Edit Hotel</Link></li>
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
                                    <li><Link className="dropdown-item" to={`/addaccount`}>Add new account</Link></li>
                                    <li><Link className="dropdown-item" to={`/editaccount`}>Edit account</Link></li>
                                    <li><Link className="dropdown-item" to={`/editaccountrole`}>Edit account's role</Link></li>
                                    <li><Link className="dropdown-item" to={`/addamenity`}>Add new amenity</Link></li>
                                    <li><Link className="dropdown-item" to={`/editamenity`}>Edit amenity</Link></li>
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