import BaseService from "./BaseService";


const login = (authObject) => BaseService.authRequest(authObject, 'login');
const register = (registerUser) => BaseService.authRequest(registerUser, 'register')

const authService = {login, register};
export default authService;
