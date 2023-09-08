import BaseService from "./BaseService";

const hotelUrl = 'hotels';

const getAllHotels = (token) => BaseService.getRequest(token, hotelUrl);
const getHotelsCities = (token) => BaseService.getRequest(token, `${hotelUrl}/cities`);
const getHotelDetails = (token, id) => BaseService.getRequest(token, `${hotelUrl}/${id}`);
const createHotel = (token, createHotelDto) => BaseService.postRequest(token, createHotelDto, hotelUrl);
const updateHotelInfo = (token, id, updateHotelDto) => BaseService.putRequest(token, id, updateHotelDto, hotelUrl, null);
const getAllRooms = (token, hotelId) => BaseService.getRequest(token, `${hotelUrl}/${hotelId}/rooms`);
const addRoomToHotel = (token, addRoomDto) => BaseService.postRequest(token, addRoomDto, 'rooms');
const updateRoom = (token, hotelId, updateRoomDto) => BaseService.putRequest(token, hotelId, updateRoomDto, 'rooms', null);
const searchHotels = (token, searchDto) => BaseService.postRequest(token, searchDto, 'search');

const HotelService = {
    getAllHotels, createHotel, updateHotelInfo, addRoomToHotel, updateRoom, getAllRooms, getHotelDetails, searchHotels, getHotelsCities
};
export default HotelService;