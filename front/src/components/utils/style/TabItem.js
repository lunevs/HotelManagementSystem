import React from "react";

const TabItem = ({name, selected, tabLabel}) => {
    return (
        <li className="nav-item" role="presentation">
            <button className={selected ? 'nav-link active' : 'nav-link'}
                    id={`${name}-tab`}
                    data-bs-toggle="tab"
                    data-bs-target={`#${name}-tab-pane`}
                    type="button"
                    role="tab"
                    aria-controls={`${name}-tab-pane`}
                    aria-selected={selected}
            >
                {tabLabel}
            </button>
        </li>
    );
}

export default TabItem;