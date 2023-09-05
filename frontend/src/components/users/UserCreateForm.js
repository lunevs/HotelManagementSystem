import React from "react";
import accountService from "../../services/AccountService";

const UserCreateForm = ({token, accounts, setAccounts}) => {

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
                const newAccounts = [...accounts, result];
                console.log(newAccounts)
                setAccounts(newAccounts);
            })
            .catch(error => console.log(error));
    }


    return (
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Create new account block:</p>
            <form onSubmit={createAccountHandler}>
                <div className="input-group mb-1">
                    <input type="text"
                           className="form-control m-2"
                           placeholder="Account name"
                           aria-label="Account name"
                           aria-describedby="addon-wrapping"
                           name="newAccountName"
                    />
                    <input type="password"
                           className="form-control m-2"
                           placeholder="Account password"
                           aria-label="Account password"
                           aria-describedby="addon-wrapping"
                           name="newAccountPassword"
                    />
                    <input type="email"
                           className="form-control m-2"
                           placeholder="Account email"
                           aria-label="Account email"
                           aria-describedby="addon-wrapping"
                           name="newAccountEmail"
                    />
                    <button className="btn btn-secondary m-2" type="submit">Create user</button>
                </div>
            </form>
        </div>
    );
}

export default UserCreateForm;