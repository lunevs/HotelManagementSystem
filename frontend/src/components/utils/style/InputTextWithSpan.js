import React from "react";

const InputTextWithSpan = ({name, type, spanLabel, value, onChange, money}) => {

    return (
        <div className="input-group mb-3">
            <span className="input-group-text" id="inputGroup-sizing-default">{spanLabel}</span>
            <input type={type}
                   className="form-control"
                   aria-label={`${spanLabel} input`}
                   aria-describedby="inputGroup-sizing-default"
                   name={name}
                   value={value}
                   onChange={onChange}
            />
            {
                money ? <span className="input-group-text">$</span> : <div />
            }
        </div>
    );
}

export default InputTextWithSpan;