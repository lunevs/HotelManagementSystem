import React from "react";

const ImageLink = ({image}) => {

    if (image.id === 0) {
        return (
            <div className="carousel-item active">
                <img src={image.url} className="d-block w-100" alt={image.alt} />
            </div>
        );
    } else {
        return (
            <div className="carousel-item">
                <img src={image.url} className="d-block w-100" alt={image.alt} />
            </div>
        );
    }
}

export default ImageLink