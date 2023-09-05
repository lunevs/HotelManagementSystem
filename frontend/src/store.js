import { configureStore } from '@reduxjs/toolkit';

import AmenityReducer from "./reducers/AmenityReducer";
import UserReducer from "./reducers/UserReducer";


const store = configureStore({
    reducer: {
        amenities: AmenityReducer,
        user: UserReducer
    }
})

export default store