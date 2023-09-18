import BaseService from "./BaseService";

const hotelUrl = 'hotels';
const roomUrl = 'rooms';

const getAllHotels = (token) => BaseService.getRequest(token, hotelUrl);
const getHotelsCities = (token) => BaseService.getRequest(token, `${hotelUrl}/cities`);
const getHotelDetails = (token, id) => BaseService.getRequest(token, `${hotelUrl}/${id}`);
const createHotel = (token, createHotelDto) => BaseService.postRequest(token, createHotelDto, hotelUrl);
const updateHotelInfo = (token, id, updateHotelDto) => BaseService.putRequest(token, id, updateHotelDto, hotelUrl, null);
const getAllRooms = (token, hotelId) => BaseService.getRequest(token, `${hotelUrl}/${hotelId}/${roomUrl}`);
const addRoomToHotel = (token, addRoomDto) => BaseService.postRequest(token, addRoomDto, roomUrl);
const updateRoom = (token, hotelId, updateRoomDto) => BaseService.putRequest(token, hotelId, updateRoomDto, roomUrl, null);
const searchHotels = (token, searchDto) => BaseService.postRequest(token, searchDto, 'search');
const deleteHotel = (token, id) => BaseService.deleteRequest(token, `${hotelUrl}/${id}`);
const deleteRoom = (token, id) => BaseService.deleteRequest(token, `${roomUrl}/${id}`);


const HotelService = {
    getAllHotels, createHotel, updateHotelInfo, addRoomToHotel, updateRoom, getAllRooms,
    getHotelDetails, searchHotels, getHotelsCities, deleteHotel, deleteRoom
};
export default HotelService;