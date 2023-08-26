import axios from "axios";

const baseUrl = 'http://localhost:8080';


const getAllAccounts = (token) => {
    const request = axios.get(
        `${baseUrl}/accounts`,{
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}

const getAllRoles = (token) => {
    const request = axios.get(
        `${baseUrl}/roles`,{
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}


const updateAccountInfo = (token, accountToUpdate) => {
    const request = axios.put(
        `${baseUrl}/accounts/${accountToUpdate.id}/info`,
        accountToUpdate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}

const updateAccountRole = (token, id, actionRoleDto) => {
    const request = axios.put(
        `${baseUrl}/accounts/${id}/role`,
        actionRoleDto,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}

const createAccount = (token, accountToCreate) => {
    const request = axios.post(
        `${baseUrl}/accounts`,
        accountToCreate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}

const AccountService = {getAllAccounts, updateAccountInfo, createAccount, getAllRoles, updateAccountRole};
export default AccountService;