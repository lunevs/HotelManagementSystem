import React from "react";

const InputCheckBox = ({id, name, value, checked, onChange, children}) => {

    return (
        <div className="form-check form-check-inline">
            <input className="form-check-input" type="radio"
                   name={name}
                   id={id}
                   value={value}
                   checked={checked}
                   onChange={onChange}
            />
            <label className="form-check-label" htmlFor={id}>{children}</label>
        </div>
    );
}


export default InputCheckBox;