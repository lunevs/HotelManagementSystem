import BaseService from "./BaseService";


const login = (authObject) => BaseService.authMethod(authObject, 'login');

const register = (registerUser) => BaseService.authMethod(registerUser, 'register')

const authService = {login, register};
export default authService;
