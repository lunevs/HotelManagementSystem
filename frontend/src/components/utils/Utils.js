

const ErrorsHandler = (error, changeStatusHandler, navigate) => {
    if (error.hasOwnProperty('response') && error.response.hasOwnProperty('data')) {
        changeStatusHandler({message: error.response.data.title, type: 'error'});
        if (error.response.data.title.includes('token')) {
            navigate('/logout');
        }
    } else {
        changeStatusHandler({message: JSON.stringify(error), type: 'error'});
    }

}

export default ErrorsHandler;