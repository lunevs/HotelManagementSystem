import { createSlice } from "@reduxjs/toolkit";
import amenityService from "../services/AmenityService";

const AmenityReducer = createSlice({
    name: 'amenities',
    initialState: [],
    reducers: {
        deleteAmenityReducer(state, action) {
            return state.filter(el => el.id !== action.payload.id)
        },
        updateAmenityReducer(state, action) {
            return state.map(el => (el.id !== action.payload.id) ? el : action.payload)
        },
        createAmenityReducer(state, action) {
            state.push(action.payload)
        },
        loadAmenitiesReducer(state, action) {
            return action.payload
        }
    }
})

export const { deleteAmenityReducer, updateAmenityReducer, loadAmenitiesReducer, createAmenityReducer } = AmenityReducer.actions

export const initializeAmenities = () => {
    return async dispatch => {
        const amenities = await amenityService.getAll()
        dispatch(loadAmenitiesReducer(amenities))
    }
}

export const createAmenity = (amenityDto) => {
    return async dispatch => {
        const newAmenity = await amenityService.createAmenity(amenityDto)
        dispatch(createAmenityReducer(newAmenity))
    }
}

export const updateAmenity = (amenityDto) => {
    return async dispatch => {
        const updatedAmenity = await amenityService.updateAmenity(amenityDto)
        dispatch(updateAmenityReducer(updatedAmenity))
    }
}

export const deleteAmenity = (amenityId) => {
    return async dispatch => {
        // await amenityService.delete ...
        dispatch(deleteAmenityReducer(amenityId))
    }
}

export default AmenityReducer.actions;
