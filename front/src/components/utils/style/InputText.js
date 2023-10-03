import React from "react";

const InputText = ({name, description, value, onChange}) => {
    return (
        <div className="input-group mb-2">
            <input type="text"
                   className="form-control m-2"
                   placeholder={description}
                   aria-label={description}
                   aria-describedby="addon-wrapping"
                   name={name}
                   value={value}
                   onChange={onChange}
            />
        </div>
    );
}

export default InputText;