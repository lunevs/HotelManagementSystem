import BaseService from "./BaseService";


const getAllAccounts = (token) => BaseService.getRequest(token, 'accounts');
const getAllRoles = (token) => BaseService.getRequest(token, 'roles');
const updateAccountInfo = (token, accountToUpdate) => BaseService.putRequest(token, accountToUpdate.id, accountToUpdate, 'accounts', 'info');
const updateAccountRole = (token, id, actionRoleDto) => BaseService.putRequest(token, id, actionRoleDto, 'accounts', 'role');
const createAccount = (token, accountToCreate) => BaseService.postRequest(token, accountToCreate, 'accounts');

const AccountService = {getAllAccounts, updateAccountInfo, createAccount, getAllRoles, updateAccountRole};
export default AccountService;