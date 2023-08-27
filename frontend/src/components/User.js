import React, {useEffect, useState} from "react";
import accountService from "../services/AccountService";
import UserCreateForm from "./UserCreateForm";
import UserEdirForm from "./UserEdirForm";
import UserChangeRole from "./UserChangeRole";

const User = ({token}) => {

    const [roles, setRoles] = useState([]);
    const [reload, setReload] = useState(false);
    const [accounts, setAccounts] = useState([]);
    const [resError, setResError] = useState('');

    useEffect(() => {
        if (token !== '') {
            accountService
                .getAllAccounts(token)
                .then(result => {
                    setAccounts(result);
                })
                .catch(error => {
                    setResError(error.response.data.title);
                });

            accountService
                .getAllRoles(token)
                .then(result => {
                    setRoles(result);
                })
                .catch(error => {
                    setResError(error.response.data.title);
                });
        }
    }, [token, reload]);

    

    if (token !== '' && resError === '') {
        return (
            <div>
                <UserCreateForm token={token} accounts={accounts} setAccounts={setAccounts} />

                <UserEdirForm token={token} accounts={accounts} setAccounts={setAccounts} />

                <UserChangeRole token={token} accounts={accounts} roles={roles} setReload={setReload} />


            </div>
        );
    } else {
        return (
            <div className="row">
                {resError !== '' ?
                    <div className="alert alert-danger w-50 mx-auto" role="alert">{resError}</div>
                    : <div className="alert alert-warning w-50 mx-auto" role="alert">authorization required</div>}

            </div>
        );
    }
}

export default User;