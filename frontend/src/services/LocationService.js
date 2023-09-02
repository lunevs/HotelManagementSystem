import BaseService from "./BaseService";


const getAllLocations = (token) => BaseService.getAll(token, 'locations');

const createLocation = (token, createLocationDto) => BaseService.createElement(token, createLocationDto, 'locations');

const updateLocationInfo = (token, id, updateLocationDto) => BaseService.updateElement(token, id, updateLocationDto, 'locations', null);

const getAllRooms = (token, locationId) => BaseService.getAll(token, `locations/${locationId}/rooms`);

const addRoomToLocation = (token, addRoomDto) => BaseService.createElement(token, addRoomDto, 'rooms');

const updateRoom = (token, locationId, updateRoomDto) => BaseService.updateElement(token, locationId, updateRoomDto, 'rooms', null);

const LocationService = {getAllLocations, createLocation, updateLocationInfo, addRoomToLocation, updateRoom, getAllRooms};
export default LocationService;