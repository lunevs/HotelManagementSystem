import React from "react";

const ImageButton = ({id, imagesId}) => {

    if (id === 0) {
        return (
            <button type="button"
                    data-bs-target={"#"+imagesId}
                    data-bs-slide-to={id}
                    className="active"
                    aria-current="true"
                    aria-label={"Slide " + id}>
            </button>
        );
    } else {
        return (
            <button type="button"
                    data-bs-target={"#"+imagesId}
                    data-bs-slide-to={id}
                    aria-label={"Slide " + id}>
            </button>
        );
    }
}

export default ImageButton