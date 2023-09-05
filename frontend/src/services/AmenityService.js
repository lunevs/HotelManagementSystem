import BaseService from "./BaseService";

let token = null

const setToken = newToken => {
    token = newToken
}

const getAll = () => BaseService.getAll(token, 'amenities');

const createAmenity = (amenityDto) => BaseService.createElement(token, amenityDto, 'amenities');

const updateAmenity = (amenityDto) => BaseService.updateElement(token, amenityDto.id, amenityDto, 'amenities', null);

const amenityService = {getAll, createAmenity, updateAmenity, setToken}
export default amenityService;
