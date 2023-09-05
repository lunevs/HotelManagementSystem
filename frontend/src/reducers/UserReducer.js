import { createSlice } from "@reduxjs/toolkit";
import amenityService from "../services/AmenityService";
import authService from "../services/AuthService";

const UserReducer = createSlice({
    name: 'user',
    initialState: {
        token: ''
    },
    reducers: {
        loginReducer(state, action) {
            window.localStorage.setItem('loggedHotelServiceUser', JSON.stringify(action.payload))
            amenityService.setToken(action.payload.token)
            console.log('login user', action.payload)
            return action.payload
        },
        logoutReducer(state, action) {
            window.localStorage.removeItem('loggedHotelServiceUser')
            amenityService.setToken(null)
            return {}
        }
    }
})

export const { loginReducer, logoutReducer } = UserReducer.actions

export const initializeUser = () => {
    return async dispatch => {
        const loggedUserJSON = window.localStorage.getItem('loggedHotelServiceUser')
        if (loggedUserJSON) {
            const user = JSON.parse(loggedUserJSON)
            dispatch(loginReducer(user))
        }
    }
}

export const loginUser = (content) => {
    return async dispatch => {
        try {
            const user = await authService.login(content)
            dispatch(loginReducer(user))
            // dispatch(statusChange({message: `Добро пожаловать ${user.username}`, type: 'success'}))
            // setTimeout(() => dispatch(statusChange({})), 3000)
        } catch(e) {
            console.log('error', e)
            // dispatch(statusChange({message: 'Ошибка авторизации', type: 'danger'}))
            // setTimeout(() => dispatch(statusChange({})), 3000)
        }
    }
}

export const logoutUser = () => {
    return async dispatch => {
        dispatch(logoutReducer())
    }
}

export default UserReducer.actions;
