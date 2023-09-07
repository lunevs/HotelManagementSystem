import React from "react";

const Button = ({children}) => {
    return (
        <button className="btn btn-secondary m-2" type="submit">
            {children}
        </button>
    );
}

export default Button;