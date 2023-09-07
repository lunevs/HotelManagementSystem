import axios from "axios";

const baseUrl = 'http://localhost:8080/api/v1';


const getAll = async (token, url) => {
    const request = await axios.get(
        `${baseUrl}/${url}`,{
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    console.log({
        method: 'GET',
        body: null,
        url: `${baseUrl}/${url}`,
        headers: {...request.headers, 'Authorization': `Bearer ${token}`}
    });
    return request.data;
}


const updateElement = async (token, id, elementToUpdate, mainUrl, secondUrl) => {
    const url = (secondUrl === null) ? `${baseUrl}/${mainUrl}/${id}` : `${baseUrl}/${mainUrl}/${id}/${secondUrl}`;
    const request = await axios.put(
        url,
        elementToUpdate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    console.log({
        method: 'PUT',
        body: elementToUpdate,
        url: url,
        headers: {...request.headers, 'Authorization': `Bearer ${token}`}
    });
    return request.data;
}

const createElement = async (token, elementToCreate, url) => {
    const request = await axios.post(
        `${baseUrl}/${url}`,
        elementToCreate,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    console.log({
        method: 'POST',
        body: elementToCreate,
        url: `${baseUrl}/${url}`,
        headers: {...request.headers, 'Authorization': `Bearer ${token}`}
    });
    return request.data;
}


const authMethod = async (authObject, url) => {
    const request = await axios.post
    (
        `${baseUrl}/${url}`,
        authObject,
        {headers: {'Content-Type': 'application/json'}}
    )
    console.log({
        method: 'POST',
        body: authObject,
        url: `${baseUrl}/${url}`,
        headers: {...request.headers, 'Content-Type': 'application/json'}
    });
    return request.data;
}

const BaseService = {getAll, updateElement, createElement, authMethod};
export default BaseService;