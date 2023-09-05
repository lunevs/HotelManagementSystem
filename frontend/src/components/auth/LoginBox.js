import React, {useState} from 'react';
import authService from "../../services/AuthService";

const LoginBox = ({changeTokenHandler, changeStatusHandler}) => {

    const [username, setUsername] = useState('');

    const signInHandler = (event) => {
        event.preventDefault();
        const authData = {
            accountName: event.target.loginName.value,
            accountPassword: event.target.loginPassword.value
        }
        authService.login(authData)
            .then(resultToken => {
                if (resultToken.token !== null) {
                    setUsername(authData.username);
                    changeTokenHandler(resultToken.token);
                    changeStatusHandler({message: "Welcome" + username, type: 'success'});
                    window.localStorage.setItem('loggedHotelServiceUser', JSON.stringify(resultToken))
                }
            })
            .catch(function (error) {
                if (error.hasOwnProperty('response')) {
                    changeStatusHandler({message: error.response.data.title, type: 'error'});
                } else {
                    changeStatusHandler({message: "unknown error: " + JSON.stringify(error), type: 'error'});
                }
            });
    }

    const registerHandler = (event) => {
        event.preventDefault();
        const registerData = {
            accountName: event.target.registerName.value,
            accountPassword: event.target.registerPassword.value,
            accountEmail: event.target.registerEmail.value
        }
        authService.register(registerData)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Successfully registered!', type: 'success'});
                    document.getElementById('registerFormId').reset();
                } else {
                    console.log(result)
                    changeStatusHandler({message: 'Unknown result!', type: 'error'});
                }
            })
            .catch(function (error) {
                if (error.hasOwnProperty('response')) {
                    changeStatusHandler({message: error.response.data.title, type: 'error'});
                } else {
                    changeStatusHandler({message: "unknown error: " + JSON.stringify(error), type: 'error'});
                }
            });
    }

    return (
        <div className="mx-auto mb-5 ">
            <ul className="nav nav-tabs mb-3" id="myTab" role="tablist">
                <li className="nav-item" role="presentation">
                    <button className="nav-link active" id="home-tab" data-bs-toggle="tab"
                            data-bs-target="#home-tab-pane" type="button" role="tab" aria-controls="home-tab-pane"
                            aria-selected="true">Login form
                    </button>
                </li>
                <li className="nav-item" role="presentation">
                    <button className="nav-link" id="profile-tab" data-bs-toggle="tab"
                            data-bs-target="#profile-tab-pane" type="button" role="tab"
                            aria-controls="profile-tab-pane" aria-selected="false">Registration
                    </button>
                </li>
            </ul>
            <div className="tab-content" id="myTabContent">
                <div className="tab-pane fade show active" id="home-tab-pane" role="tabpanel"
                     aria-labelledby="home-tab" tabIndex="0">

                    <form onSubmit={signInHandler}>
                        <div className="input-group mb-3">
                            <span className="input-group-text" id="inputGroup-sizing-default">Username</span>
                            <input type="text" className="form-control"
                                   aria-label="Username input"
                                   aria-describedby="inputGroup-sizing-default"
                                   name="loginName"
                            />
                        </div>
                        <div className="input-group mb-3">
                            <span className="input-group-text" id="inputGroup-sizing-default">Password</span>
                            <input type="password" className="form-control"
                                   aria-label="Password input"
                                   aria-describedby="inputGroup-sizing-default"
                                   name="loginPassword"
                            />
                        </div>
                        <button type="submit" className="btn btn-secondary m-2">Sign in</button>
                    </form>


                </div>
                <div className="tab-pane fade" id="profile-tab-pane" role="tabpanel" aria-labelledby="profile-tab"
                     tabIndex="0">

                    <form onSubmit={registerHandler} id="registerFormId">
                        <div className="input-group mb-3">
                            <span className="input-group-text" id="inputGroup-sizing-default">Username</span>
                            <input type="text" className="form-control"
                                   aria-label="Username input"
                                   aria-describedby="inputGroup-sizing-default"
                                   name="registerName"
                            />
                        </div>
                        <div className="input-group mb-3">
                            <span className="input-group-text" id="inputGroup-sizing-default">Password</span>
                            <input type="password" className="form-control"
                                   aria-label="Password input"
                                   aria-describedby="inputGroup-sizing-default"
                                   name="registerPassword"
                            />
                        </div>
                        <div className="input-group mb-3">
                            <span className="input-group-text" id="inputGroup-sizing-default">Email</span>
                            <input type="email" className="form-control"
                                   aria-label="Email input"
                                   aria-describedby="inputGroup-sizing-default"
                                   name="registerEmail"
                            />
                        </div>
                        <button type="submit" className="btn btn-secondary m-2">Register</button>
                    </form>


                </div>
            </div>

        </div>
    );

}

export default LoginBox;
