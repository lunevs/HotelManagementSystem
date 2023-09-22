import axios from "axios";

const baseUrl = 'http://localhost:8080/api/v1';


const getRequest = async (token, url) => {
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


const putRequest = async (token, id, elementToUpdate, mainUrl, secondUrl) => {
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

const postRequest = async (token, elementToCreate, url) => {
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
    console.log(request);
    return request.data;
}

const deleteRequest = async (token, url) => {
    const request = await axios.delete(
        `${baseUrl}/${url}`,
        {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });
    console.log({
        method: 'DELETE',
        body: null,
        url: `${baseUrl}/${url}`,
        headers: {...request.headers, 'Authorization': `Bearer ${token}`}
    });
    console.log(request);
    return request.data;
}

const authRequest = async (authObject, url) => {
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

const sendFile = async (formData) => {
    const request = await axios.post(
        `${baseUrl}/upload`,
        formData,
        {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }
    )
    console.log({
        method: 'POST',
        body: 'file',
        url: `${baseUrl}/upload`,
        headers: {...request.headers, 'Content-Type': 'application/json'}
    });
    return request.data;
}

const BaseService = {getRequest, putRequest, postRequest, authRequest, deleteRequest, sendFile};
export default BaseService;