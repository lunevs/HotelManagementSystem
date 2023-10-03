import React, {useState} from 'react';
import authService from "../../services/AuthService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import Button from "../utils/style/Button";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";
import TabItem from "../utils/style/TabItem";
import TabBody from "../utils/style/TabBody";

const LoginBox = ({changeTokenHandler, changeStatusHandler}) => {

    const navigate = useNavigate();
    const [username, setUsername] = useState('');

    const signInHandler = (event) => {
        event.preventDefault();
        const authData = {
            accountName: event.target.loginName.value,
            accountPassword: event.target.loginPassword.value
        }
        authService
            .login(authData)
            .then(resultToken => {
                if (resultToken.token !== null) {
                    setUsername(authData.username);
                    changeTokenHandler(resultToken.token);
                    changeStatusHandler({message: "Welcome" + username, type: 'success'});
                    window.localStorage.setItem('loggedHotelServiceUser', JSON.stringify(resultToken))
                } else {
                    changeStatusHandler({message: 'Unknown result data', type: 'error'});
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
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
                <TabItem name="home" selected={true} tabLabel="Login form" />
                <TabItem name="profile" selected={false} tabLabel="Registration form" />
            </ul>
            <div className="tab-content" id="myTabContent">
                <TabBody name="home" selected={true}>
                    <form onSubmit={signInHandler}>
                        <InputTextWithSpan type="text" name="loginName" spanLabel="Username" />
                        <InputTextWithSpan type="password" name="loginPassword" spanLabel="Password" />
                        <Button>Sign in</Button>
                    </form>
                </TabBody>
                <TabBody name="profile" selected={false}>
                    <form onSubmit={registerHandler} id="registerFormId">
                        <InputTextWithSpan type="text" name="registerName" spanLabel="Username" />
                        <InputTextWithSpan type="password" name="registerPassword" spanLabel="Password" />
                        <InputTextWithSpan type="email" name="registerEmail" spanLabel="Email" />
                        <Button>Register</Button>
                    </form>
                </TabBody>
            </div>

        </div>
    );

}

export default LoginBox;
