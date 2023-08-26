import React, {useState} from 'react';
import authService from "../services/AuthService";

const LoginBox = ({changeTokenHandler}) => {

    const [isAuth, setIsAuth] = useState(false);
    const [username, setUsername] = useState('');
    const [error, setError] = useState('');
    const [regStatus, setRegStatus] = useState('');

    const signInHandler = (event) => {
        event.preventDefault();
        const authData = {
            accountName: event.target.loginName.value,
            accountPassword: event.target.loginPassword.value
        }
        authService.login(authData)
            .then(resultToken => {
                if (resultToken.token !== null) {
                    setIsAuth(true);
                    setUsername(authData.username);
                    changeTokenHandler(resultToken.token);
                }
            })
            .catch(function (error) {
                if (error.hasOwnProperty('response')) {
                    setError(error.response.data.title);
                } else {
                    setError("unknown error: " + JSON.stringify(error));
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
                    setRegStatus('Successfully registered!');
                    document.getElementById('registerFormId').reset();
                } else {
                    console.log(result)
                    setRegStatus('Unknown result!')
                }
            })
            .catch(function (error) {
                if (error.hasOwnProperty('response')) {
                    setError(error.response.data.title);
                } else {
                    setError("unknown error: " + JSON.stringify(error));
                }
            });
    }

    if (isAuth) {
        return (
            <div className="mx-auto mb-5">
                <div className="alert alert-success" role="alert">Welcome {username}</div>
                <a href="http://localhost:3000/" className="btn btn-secondary" >logout</a>
            </div>
        );
    } else {
        return (
            <div className="mx-auto mb-5">
                {error !== '' ? <div className="alert alert-danger" role="alert">{error}</div> : <div />}

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

                        {regStatus !== '' ? <div className="alert alert-warning" role="alert">{regStatus}</div> : <div />}

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

}

export default LoginBox;
