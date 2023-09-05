import BaseService from "./BaseService";

const getAll = (token) => BaseService.getAll(token, 'bookings')

const bookRoom = (token, bookDto) => BaseService.createElement(token, bookDto, 'bookings');

const BookingService = {getAll, bookRoom};
export default BookingService;