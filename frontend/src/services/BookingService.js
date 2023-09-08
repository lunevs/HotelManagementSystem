import BaseService from "./BaseService";

const getAll = (token) => BaseService.getRequest(token, 'bookings')
const bookRoom = (token, bookDto) => BaseService.postRequest(token, bookDto, 'bookings');

const BookingService = {getAll, bookRoom};
export default BookingService;