import {
    LOGIN_FAILURE,
    LOGIN_REQUEST,
    LOGIN_SUCCESS,
    LOGOUT_FAILURE,
    LOGOUT_REQUEST,
    LOGOUT_SUCCESS,
    REGISTER_FAILURE,
    REGISTER_REQUEST,
    REGISTER_SUCCESS
} from './actionTypes';
import * as authService from '../service/authService';
import * as alertActions from './alertActions';
import {history} from '../helpers/history';
import {API_CALL} from '../helpers/callApiMiddleware';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const queryLogin = (username, password) => ({
    types: [LOGIN_REQUEST, LOGIN_SUCCESS, LOGIN_FAILURE],
    [API_CALL]: () => authService.loginUser(username, password),
    payload: {username},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            history.push('/')
        },
        onFailure: (dispatch, error) => dispatch(alertActions.error(error.message))
    }
});

export const queryLogout = () => ({
    types: [LOGOUT_REQUEST, LOGOUT_SUCCESS, LOGOUT_FAILURE],
    [API_CALL]: () => authService.logoutUser(),
    shouldCallApi : () => localStorage.hasOwnProperty('user'),
    callbacks: {
        onSuccess: () => {
            localStorage.removeItem('user');
            history.push('/login')
        },
        onFailure: (dispatch, error) => dispatch(alertActions.error(error.message))
    }
});

export const queryRegister = (user) => ({
    types: [REGISTER_REQUEST, REGISTER_SUCCESS, REGISTER_FAILURE],
    [API_CALL]: () => authService.registerUser(user),
    payload: {user},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            history.push('/login');
            dispatch(alertActions.success('Registration successful'));
        },
        onFailure: (dispatch, error) => dispatch(alertActions.error(error.message))
    }
});

