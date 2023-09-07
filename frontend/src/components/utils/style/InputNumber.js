import React from "react";

const InputNumber = ({name, description, step = "0.01", value, onChange}) => {
    return (
        <div className="input-group mb-1">
            <input type="number"
                   className="form-control"
                   placeholder={description}
                   aria-label={description}
                   aria-describedby="addon-wrapping"
                   step={step}
                   name={name}
                   value={value}
                   onChange={onChange}
            />
            <span className="input-group-text">$</span>
            <span className="input-group-text">0.00</span>
        </div>
    );
}

export default InputNumber;