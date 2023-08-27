import React from "react";

const Status = ({message}) => {
    return (
        <div>
            {message !== '' ? <div className="alert alert-success" role="alert">{message}</div> : <div />}
        </div>
);
}

export default Status;