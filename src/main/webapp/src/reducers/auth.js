import {
    LOGIN_FAILURE,
    LOGIN_REQUEST,
    LOGIN_SUCCESS,
    LOGOUT_FAILURE,
    LOGOUT_REQUEST,
    LOGOUT_SUCCESS
} from '../actions/actionTypes';
import createReducer from "../helpers/createReducer";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
let user = JSON.parse(localStorage.getItem('user'));
const initialState = user ? {loggedIn: true, user} : {};

export const auth = createReducer(initialState, {
    [LOGIN_REQUEST]: (state, action) => ({loggingIn: true, user: action.username})
    , [LOGIN_SUCCESS]: (state, action) => ({loggedIn: true, loggingIn: false, user: action.data})
    , [LOGIN_FAILURE]: (state, action) => ({...state, loggingIn: false, error: action.error})

    , [LOGOUT_REQUEST]: (state, action) => ({loggingOut: true, user: action.username})
    , [LOGOUT_SUCCESS]: (state, action) => ({loggingOut: false, user: {}})
    , [LOGOUT_FAILURE]: (state, action) => ({...state, loggingOut: false, error: action.error})
});
