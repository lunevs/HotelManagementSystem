import React from "react";

const BoxDiv = ({title, children}) => {
    return (
        <div className="row border border-success-subtle mt-4 mx-2 rounded-2 shadow-sm">
            {title !== undefined ? <p className="text-start text-secondary">{title}</p> : <div />}
            {children}
        </div>
    );
}

export default BoxDiv;