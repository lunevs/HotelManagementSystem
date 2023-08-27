import React, {useState} from "react";
import accountService from "../services/AccountService";

const UserEdirForm = ({token, accounts, setAccounts}) => {

    const [selectedAccount, setSelectedAccount] = useState({
        accountName: '',
        accountEmail: '',
        id: 0,
        role: {}
    });

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
        console.dir(accountToUpdate);
        accountService
            .updateAccountInfo(token, accountToUpdate)
            .then(result => {
                setAccounts(accounts.map(el => {
                    if (el.id === result.id) {
                        return result;
                    } else {
                        return el;
                    }
                }))
            })
            .catch(error => console.log(error))
    }


    const selectAccountHandler = (event) => {
        let account = accounts.filter(el => el.id.toString() === event.target.value)[0];
        if (account !== undefined) {
            setSelectedAccount(account);
        }
    }


    return (
        <div className="row border border-success-subtle mt-4 mx-2 pb-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Edit account info block:</p>

            <div className="row">
                <div className="col-3">
                    <select className="form-select" size="5" aria-label="All accounts list" onChange={selectAccountHandler}>
                        {accounts.map(el => <option value={el.id} key={el.id}>{el.accountName}</option>)}
                    </select>
                </div>
                <div className="col-9">
                    <form>
                        <div className="input-group mb-3">
                            <span className="input-group-text w-25" id="addon-wrapping">username</span>
                            <input type="text" className="form-control"
                                   placeholder="Username"
                                   aria-label="Username"
                                   aria-describedby="addon-wrapping"
                                   name="accountName"
                                   value={selectedAccount.accountName}
                                   onChange={e => setSelectedAccount({...selectedAccount, accountName: e.target.value})}
                            />
                        </div>

                        <div className="input-group mb-3">
                            <span className="input-group-text w-25" id="addon-wrapping">email</span>
                            <input type="text"
                                   id="AccountEmailInputId"
                                   className="form-control"
                                   placeholder="Email"
                                   aria-label="Email"
                                   aria-describedby="addon-wrapping"
                                   name="accountEmail"
                                   value={selectedAccount.accountEmail}
                                   onChange={e => setSelectedAccount({...selectedAccount, accountEmail: e.target.value})}
                            />
                        </div>

                        <button className="btn btn-secondary" type="button" onClick={updateAccountHandler}>Update user</button>
                    </form>

                </div>
            </div>
        </div>

    );
}


export default UserEdirForm;