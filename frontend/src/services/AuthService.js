import axios from "axios";

const baseUrl = 'http://localhost:8080';


const login = (authObject) => {
    const request = axios.post
    (
        `${baseUrl}/login`,
        authObject,
        //qs.stringify(authObject),
        {headers: {'Content-Type': 'application/json'}}
    )
    return request.then(response => response.data);
}

const register = (registerUser) => {
    const request = axios.post
    (
        `${baseUrl}/register`,
        registerUser,
        {headers: {'Content-Type': 'application/json'}}
    )
    return request.then(response => response.data);
}


const authService = {login, register};
export default authService;
