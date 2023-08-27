import axios from "axios";

const baseUrl = 'http://localhost:8080';


const getAll = (token, url) => {
    const request = axios.get(
        `${baseUrl}/${url}`,{
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}


const updateElement = (token, id, elementToUpdate, mainUrl, secondUrl) => {
    const url = (secondUrl === null) ? `${baseUrl}/${mainUrl}/${id}` : `${baseUrl}/${mainUrl}/${id}/${secondUrl}`;
    const request = axios.put(
        url,
        elementToUpdate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}

const createElement = (token, elementToCreate, url) => {
    const request = axios.post(
        `${baseUrl}/${url}`,
        elementToCreate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    return request.then(response => response.data);
}


const authMethod = (authObject, url) => {
    const request = axios.post
    (
        `${baseUrl}/${url}`,
        authObject,
        {headers: {'Content-Type': 'application/json'}}
    )
    return request.then(response => response.data);
}

const BaseService = {getAll, updateElement, createElement, authMethod};
export default BaseService;