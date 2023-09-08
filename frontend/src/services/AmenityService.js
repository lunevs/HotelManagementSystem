import BaseService from "./BaseService";


const getAll = (token) => BaseService.getRequest(token, 'amenities');
const createAmenity = (token, amenityDto) => BaseService.postRequest(token, amenityDto, 'amenities');
const updateAmenity = (token, amenityDto) => BaseService.putRequest(token, amenityDto.id, amenityDto, 'amenities', null);

const amenityService = {getAll, createAmenity, updateAmenity}
export default amenityService;
