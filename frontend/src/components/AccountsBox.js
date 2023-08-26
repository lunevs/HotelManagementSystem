import React, {useEffect, useState} from "react";
import accountService from "../services/AccountService";


const AccountsBox = ({token}) => {

    const [reload, setReload] = useState(false);
    const [accounts, setAccounts] = useState([]);
    const [addStatus, setAddStatus] = useState('');
    const [roles, setRoles] = useState([]);
    const [accountsAddRole, setAccountsAddRole] = useState([]);
    const [resError, setResError] = useState('');
    const [selectedAccount, setSelectedAccount] = useState({
        accountName: '',
        accountEmail: '',
        id: 0,
        role: {}
    });
    const [selectedAccountRole, setSelectedAccountRole] = useState({
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
                    setAccounts(result);
                    setAccountsAddRole(result);
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

    const selectAccountHandler = (event) => {
        let account = accounts.filter(el => el.id.toString() === event.target.value)[0];
        if (account !== undefined) {
            setSelectedAccount(account);
        }
    }

    const selectAccountRoleHandler = (event) => {
        if (event.target.value === "---") {
            setSelectedAccountRole({...selectedAccountRole, role: {name: ''} });
        } else {
            let account = accountsAddRole.filter(el => el.id.toString() === event.target.value)[0];
            if (account !== undefined) {
                setSelectedAccountRole(account);
            }
        }
    }

    const updateRoleHandler = (event) => {
        event.preventDefault();
        const id = event.target.addRoleToAccount.value;
        const roleValue = event.target.addRoleValue.value.substring(5);
        const updateRoleDto = {
            role: roleValue,
            description: ''
        }
        accountService
            .updateAccountRole(token, id, updateRoleDto)
            .then(result => {
                if (result.hasOwnProperty('id')) {
                    setAddStatus('Successfully added!');
                    setTimeout(
                        () => setAddStatus(''),
                        1000
                    );
                    setReload(true);
                    document.getElementById('updateRoleFormId').reset();
                    setSelectedAccountRole({...selectedAccountRole, role: {name: ''} });

                }
            })
            .catch(err => console.log(err));
    }


    if (token !== '' && resError === '') {
        return (
            <div>

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


                <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
                    <p className="text-start text-secondary">Change user role:</p>
                    {addStatus !== '' ? <div className="alert alert-success" role="alert">{addStatus}</div> : <div />}
                    <form onSubmit={updateRoleHandler} id="updateRoleFormId">
                        <div className="row g-3">
                            <div className="col">
                                <label htmlFor="updateAccountRoleSelect" className="form-label">User:</label>
                                <select className="form-select" size="1" aria-label="All accounts list"
                                        name="addRoleToAccount"
                                        id="updateAccountRoleSelect"
                                        onChange={selectAccountRoleHandler}
                                >
                                    <option value="---" key="0">---</option>
                                    {accountsAddRole.map(el => <option value={el.id} key={el.id}>{el.accountName}</option>)}
                                </select>
                            </div>
                            <div className="col">
                                <label htmlFor="updateAccountCurrentRole" className="form-label">Current role:</label>
                                <input type="text"
                                       className="form-control"
                                       placeholder="User role"
                                       aria-label="User role"
                                       aria-describedby="addon-wrapping"
                                       readOnly={true}
                                       value={selectedAccountRole.hasOwnProperty('role') ? selectedAccountRole.role.name : ''}
                                       onChange={() => console.log()}
                                />
                            </div>
                            <div className="col">
                                <label htmlFor="updateAccountRolesList" className="form-label">New role:</label>
                                <select className="form-select" size="1" aria-label="All accounts list" name="addRoleValue" id="updateAccountRolesList">
                                    {roles.filter(el => el.name !== selectedAccountRole.role.name).map(el => <option value={el.name} key={el.id}>{el.name}</option>)}
                                </select>
                            </div>
                        </div>
                        <button className="btn btn-secondary m-2" type="submit">Update role</button>
                    </form>
                </div>



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

export default AccountsBox;