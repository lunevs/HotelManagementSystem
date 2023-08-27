import BaseService from "./BaseService";


const getAllAccounts = (token) => BaseService.getAll(token, 'accounts');

const getAllRoles = (token) => BaseService.getAll(token, 'roles');

const updateAccountInfo = (token, accountToUpdate) => BaseService.updateElement(token, accountToUpdate.id, accountToUpdate, 'accounts', 'info');

const updateAccountRole = (token, id, actionRoleDto) => BaseService.updateElement(token, id, actionRoleDto, 'accounts', 'role');

const createAccount = (token, accountToCreate) => BaseService.createElement(token, accountToCreate, 'accounts');

const AccountService = {getAllAccounts, updateAccountInfo, createAccount, getAllRoles, updateAccountRole};
export default AccountService;