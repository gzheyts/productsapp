import api from '../remote/api';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export function queryProductPage(params) {
    return api.get('/api/products', params);
}

export function queryProductUpdate(payload) {
    return api.put('/api/products', payload);
}

export function queryProductDelete(id) {
    return api.delete(`/api/products/${id}`);
}

export function queryProductCreate(payload) {
    return api.post('/api/products', payload);
}
