import BaseService from "./BaseService";

const getAll = (token) => BaseService.getAll(token, 'amenities');

const createAmenity = (token, amenityDto) => BaseService.createElement(token, amenityDto, 'amenities');

const updateAmenity = (token ,amenityDto) => BaseService.updateElement(token, amenityDto.id, amenityDto, 'amenities', null);

const amenityService = {getAll, createAmenity, updateAmenity}
export default amenityService;
