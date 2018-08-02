import {decode} from 'jsonwebtoken';
import api from '../remote/api';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export function loginUser(username, password) {
    return api.post('/login', {username, password})
        .then(handleLoginResponse);
}

export function logoutUser() {
    return api.get('/logout')
}

export function getAllUsers() {
    return api.get('/users');
}

export function getUserById(id) {
    return api.get(`/users/${id}`);
}

export function registerUser(user) {
    return api.post(`/users/register`, user);
}

export function updateUser(user) {
    return api.put(`/users/${user.id}`, user);
}

export function deleteUser(id) {
    return api.delete(`/users/${id}`);
}

function handleLoginResponse(response) {
    if (response.status !== 200 || !response.headers['authorization']) {
        const error = (response.data && response.data.message) || response.statusText;
        console.error(error);
        return Promise.reject(error);
    }

    let token = response.headers['authorization'].substring(7);
    let user = {token, username: decode(token).sub};
    localStorage.setItem('user', JSON.stringify(user));
    return {data: user};
}

