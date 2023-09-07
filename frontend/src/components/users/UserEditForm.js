/* eslint-disable */
import React, {useEffect, useState} from "react";
import accountService from "../../services/AccountService";
import {useNavigate} from "react-router-dom";
import ErrorsHandler from "../utils/Utils";
import BoxDiv from "../utils/style/BoxDiv";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";
import Button from "../utils/style/Button";

const UserEditForm = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState({
        accountName: '',
        accountEmail: '',
        id: 0,
        role: {}
    });

    useEffect(() => {
        if (token !== '') {
            accountService
                .getAllAccounts(token)
                .then(result => {
                    if (Array.isArray(result)) {
                        setAccounts(result);
                    }
                })
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

    const updateAccountHandler = (event) => {
        event.preventDefault();
        if (selectedAccount.accountName === '') {
            return;
        }
        const accountToUpdate = {
            id: selectedAccount.id,
            accountName: selectedAccount.accountName,
            accountEmail: selectedAccount.accountEmail
        };
        accountService
            .updateAccountInfo(token, accountToUpdate)
            .then(result => {
                changeStatusHandler({message: 'Account successfully updated!', type: 'success'});
                setAccounts(accounts.map(el => {
                    if (el.id === result.id) {
                        return result;
                    } else {
                        return el;
                    }
                }))
            })
            .catch(error => ErrorsHandler(error, changeStatusHandler, navigate))
    }

    const selectAccountHandler = (event) => {
        let account = accounts.filter(el => el.id.toString() === event.target.value)[0];
        if (account !== undefined) {
            setSelectedAccount(account);
        }
    }

    return (
        <BoxDiv title="Edit account info:">

            <div className="row">
                <div className="col-3">
                    <select className="form-select" size="5" aria-label="All accounts list" onChange={selectAccountHandler}>
                        {accounts.map(el => <option value={el.id} key={el.id}>{el.accountName}</option>)}
                    </select>
                </div>
                <div className="col-9">
                    <form onSubmit={updateAccountHandler}>
                        <InputTextWithSpan type="text" name="accountName" spanLabel="Username"
                                   value={selectedAccount.accountName}
                                   onChange={e => setSelectedAccount({...selectedAccount, accountName: e.target.value})}
                        />
                        <InputTextWithSpan type="text" name="accountEmail" spanLabel="Email"
                                   value={selectedAccount.accountEmail}
                                   onChange={e => setSelectedAccount({...selectedAccount, accountEmail: e.target.value})}
                        />
                        <Button>Update user</Button>
                    </form>

                </div>
            </div>
        </BoxDiv>

    );
}


export default UserEditForm;