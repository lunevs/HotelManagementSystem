import React from "react";

const AmenityElementCheckbox = ({el_id, el_name, el_price, checked, onChange}) => {


    return (
        <div className="col-3">
            <div className="form-check float-start">
                <input className="form-check-input"
                       type="checkbox"
                       id={"inlineCheckbox" + el_id}
                       value={el_id}
                       name="amenitiesList"
                       checked={checked}
                       onChange={onChange}
                />
                <label className="form-check-label" htmlFor={"inlineCheckbox" + el_id}>{el_name} (стоимость: {el_price}$)</label>
            </div>
        </div>
    );
}

export default AmenityElementCheckbox;
