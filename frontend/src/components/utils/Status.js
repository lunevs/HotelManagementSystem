import React from "react";

const Status = ({message, type}) => {
    return (
        <div>
            {
                message !== '' ?
                    type === 'success' ?
                        <div className="alert alert-success" role="alert">{message}</div>
                        :
                        <div className="alert alert-danger" role="alert">{message}</div>
                    :
                <div />
            }
        </div>
);
}

export default Status;