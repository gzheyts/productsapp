import axios from 'axios';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const apiBaseUrl = 'http://localhost:8080';

function authHeader() {
    let defaultHeaders = {
        'Content-Type': 'application/json',
        'Accept': ['application/hal+json', 'application/schema+json']
    };
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        return {...defaultHeaders, 'Authorization': 'Bearer ' + user.token};
    } else {
        return defaultHeaders;
    }
}

const api = axios.create({
    baseURL: apiBaseUrl
});

api.interceptors.request.use(config => {
        config.headers = authHeader();
        return config;
    }
    , error => {
        return Promise.reject(error);
    }
);

export default api;
