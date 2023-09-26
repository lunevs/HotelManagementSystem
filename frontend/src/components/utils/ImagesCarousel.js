import React from "react";
import ImageButton from "./ImageButton";
import ImageLink from "./ImageLink";

const ImagesCarousel = ({images, hotelId}) => {
    const imagesId = "carouselExampleIndicators" + hotelId;
    return (
        <div id={imagesId} className="carousel slide">
            <div className="carousel-indicators">
                {images.map(image => <ImageButton id={image.id} imagesId={imagesId} key={image.id} />)}
            </div>
            <div className="carousel-inner">
                {images.map(el => <ImageLink image={el} key={el.id} />)}
            </div>
            <button className="carousel-control-prev"
                    type="button"
                    data-bs-target={"#" + imagesId}
                    data-bs-slide="prev">
                <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Previous</span>
            </button>
            <button className="carousel-control-next"
                    type="button"
                    data-bs-target={"#" + imagesId}
                    data-bs-slide="next">
                <span className="carousel-control-next-icon" aria-hidden="true"></span>
                <span className="visually-hidden">Next</span>
            </button>
        </div>
    );
}

export default ImagesCarousel;