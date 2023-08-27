import React, {useState} from "react";
import accountService from "../services/AccountService";
import Status from "./Status";


const UserChangeRole = ({token, roles, accounts, setReload}) => {

    const [addStatus, setAddStatus] = useState('');
    const [selectedAccountRole, setSelectedAccountRole] = useState({
        accountName: '',
        accountEmail: '',
        id: 0,
        role: {}
    });

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
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            <p className="text-start text-secondary">Change user role:</p>
                <Status message={addStatus} />
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
        </div>

    );
}


export default UserChangeRole;