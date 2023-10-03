import BaseService from "./BaseService";

const getAll = (token) => BaseService.getRequest(token, 'bookings')
const bookRoom = (token, bookDto) => BaseService.postRequest(token, bookDto, 'bookings');
const cancelBooking = (token, id) => BaseService.deleteRequest(token, `bookings/${id}`);

const BookingService = {getAll, bookRoom, cancelBooking};
export default BookingService;