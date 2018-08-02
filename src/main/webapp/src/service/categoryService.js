import api from '../remote/api';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export function queryCategoryPage(params) {
    return api.get('/api/categories', params);
}

export function queryCategorySearch(query, page = 0) {
    return api.get(`/api/categories/search?name=${query}&page=${page}&size=4`)
        .then((resp) => resp.data)
        .then(({content: items, total: total_count}) => {
            const options = items.map(({id, name}) => ({
                id, name
            }));
            return {options, total_count};
        });
}

export function queryCategoryUpdate(payload) {
    return api.put('/api/categories', payload);
}

export function queryCategoryDelete(id) {
    return api.delete(`/api/categories/${id}`);
}

export function queryCategoryCreate(payload) {
    return api.post('/api/categories', payload);
}
