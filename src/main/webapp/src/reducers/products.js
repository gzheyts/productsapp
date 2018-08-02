import {
    MARK_PRODUCT,
    PRODUCT_CREATE_FAILURE,
    PRODUCT_CREATE_REQUEST,
    PRODUCT_CREATE_SUCCESS,
    PRODUCT_DELETE_FAILURE,
    PRODUCT_DELETE_REQUEST,
    PRODUCT_DELETE_SUCCESS,
    PRODUCT_PAGE_FAILURE,
    PRODUCT_PAGE_REQUEST,
    PRODUCT_PAGE_SUCCESS,
    PRODUCT_UPDATE_FAILURE,
    PRODUCT_UPDATE_REQUEST,
    PRODUCT_UPDATE_SUCCESS,
    TOGGLE_CREATE_PRODUCT_FORM,
    TOGGLE_EDIT_PRODUCT_FORM
} from '../actions/actionTypes';
import createReducer from "../helpers/createReducer";
import {fail, formErrors, formToggle, formValues, merge, request, success} from "./util";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
let initialState = {
    isLoading: false, content: [], page: 0, pageSize: 0, total: 0, errors: {},
    forms: {
        create: {isOpen: false, values: {}, errors: {}},
        update: {isOpen: false, values: {}, errors: {}}
    }
};

export const products = createReducer(initialState, {
    [PRODUCT_PAGE_REQUEST]: (state, action) => request(state),
    [PRODUCT_PAGE_SUCCESS]: (state, action) => merge(success(state), action.data),
    [PRODUCT_PAGE_FAILURE]: (state, action) => fail(state),

    [PRODUCT_DELETE_REQUEST]: (state, action) => request(state),
    [PRODUCT_DELETE_SUCCESS]: (state, action) => success(state),
    [PRODUCT_DELETE_FAILURE]: (state, action) => fail(state),

    [PRODUCT_UPDATE_REQUEST]: (state, action) => request(state),
    [PRODUCT_UPDATE_SUCCESS]: (state, action) => merge(success(state), formErrors(state, 'update', {})),
    [PRODUCT_UPDATE_FAILURE]: (state, action) => merge(fail(state), formErrors(state, 'update', action.errors)),

    [PRODUCT_CREATE_REQUEST]: (state, action) => request(state),
    [PRODUCT_CREATE_SUCCESS]: (state, action) => merge(success(state), formErrors(state, 'create', {})),
    [PRODUCT_CREATE_FAILURE]: (state, action) => merge(fail(state), formErrors(state, 'create', action.errors)),

    [MARK_PRODUCT]: (state, action) => merge(state, formValues(state, 'update', action.product)),

    [TOGGLE_CREATE_PRODUCT_FORM]: (state, action) => merge(state, formToggle(state, 'create')),
    [TOGGLE_EDIT_PRODUCT_FORM]: (state, action) => merge(state, formToggle(state, 'update')),
});
