import React from "react";
import accountService from "../../services/AccountService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import InputText from "../utils/style/InputText";
import Button from "../utils/style/Button";

const UserCreateForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const createAccountHandler = (event) => {
        event.preventDefault();
        const newAccount = {
            accountName: event.target.newAccountName.value,
            accountPassword: event.target.newAccountPassword.value,
            accountEmail: event.target.newAccountEmail.value
        }
        accountService
            .createAccount(token, newAccount)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    changeStatusHandler({message: 'Account successfully created!', type: 'success'})
                    document.getElementById('createUserFormId').reset();
                } else {
                    changeStatusHandler({message: 'Unknown error', type: 'error'})
                }
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
    }


    return (
        <BoxDiv title="Create new account block:">
            <form onSubmit={createAccountHandler} id="createUserFormId">
                <InputText name="newAccountName" description="Account name" />
                <InputText name="newAccountPassword" description="Account password" />
                <InputText name="newAccountEmail" description="Account email" />
                <Button>Create user</Button>
            </form>
        </BoxDiv>
    );
}

export default UserCreateForm;