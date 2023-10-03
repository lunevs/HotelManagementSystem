/* eslint-disable */
import React, {useEffect, useState} from "react";
import accountService from "../../services/AccountService";
import ErrorsHandler from "../utils/Utils";
import {useNavigate} from "react-router-dom";
import BoxDiv from "../utils/style/BoxDiv";
import InputTextWithSpan from "../utils/style/InputTextWithSpan";


const UserChangeRole = ({token, changeStatusHandler}) => {

    const navigate = useNavigate();

    const [roles, setRoles] = useState([]);
    const [accounts, setAccounts] = useState([]);
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
                .then(result => setAccounts(result))
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

    useEffect(() => {
        if (token !== '') {
            accountService
                .getAllRoles(token)
                .then(result => setRoles(result))
                .catch(error => ErrorsHandler(error, changeStatusHandler, navigate));
        }
    }, [token]);

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
                    changeStatusHandler({message: 'Role successfully updated!', type: 'success'})
                    document.getElementById('updateRoleFormId').reset();
                    setSelectedAccountRole({...selectedAccountRole, role: {name: ''} });
                }
            })
            .catch(err => ErrorsHandler(err, changeStatusHandler, navigate));
    }

    const selectAccountRoleHandler = (event) => {
        if (event.target.value === "---") {
            setSelectedAccountRole({...selectedAccountRole, role: {name: ''} });
        } else {
            let account = accounts.filter(el => el.id.toString() === event.target.value)[0];
            if (account !== undefined) {
                setSelectedAccountRole(account);
            }
        }
    }


    return(
        <BoxDiv title="Change user role:" >
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
                            {accounts.map(el => <option value={el.id} key={el.id}>{el.accountName}</option>)}
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
        </BoxDiv>

    );
}


export default UserChangeRole;