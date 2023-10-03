import React from "react";

const TabBody = ({name, selected, children}) => {
    return (
        <div className={"tab-pane fade show" + (selected ? " active" : "")}
             id={`${name}-tab-pane`}
             role="tabpanel"
             aria-labelledby={`${name}-tab`}
             tabIndex="0">
            {children}
        </div>
        );
}

export default TabBody;