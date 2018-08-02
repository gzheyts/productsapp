import {
    CATEGORY_CREATE_FAILURE,
    CATEGORY_CREATE_REQUEST,
    CATEGORY_CREATE_SUCCESS,
    CATEGORY_DELETE_FAILURE,
    CATEGORY_DELETE_REQUEST,
    CATEGORY_DELETE_SUCCESS,
    CATEGORY_PAGE_REQUEST,
    CATEGORY_PAGE_SUCCESS,
    CATEGORY_UPDATE_FAILURE,
    CATEGORY_UPDATE_REQUEST,
    CATEGORY_UPDATE_SUCCESS,
    MARK_CATEGORY,
    TOGGLE_CREATE_CATEGORY_FORM,
    TOGGLE_EDIT_CATEGORY_FORM
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
export const categories = createReducer(initialState, {

    [CATEGORY_PAGE_REQUEST]: (state, action) => request(state),
    [CATEGORY_PAGE_SUCCESS]: (state, action) => merge(success(state), action.data),
    [CATEGORY_PAGE_REQUEST]: (state, action) => fail(state),

    [CATEGORY_DELETE_REQUEST]: (state, action) => request(state),
    [CATEGORY_DELETE_SUCCESS]: (state, action) => success(state),
    [CATEGORY_DELETE_FAILURE]: (state, action) => fail(state),

    [CATEGORY_UPDATE_REQUEST]: (state, action) => request(state),
    [CATEGORY_UPDATE_SUCCESS]: (state, action) => merge(success(state), formErrors(state, 'update', {})),
    [CATEGORY_UPDATE_FAILURE]: (state, action) => merge(fail(state), formErrors(state, 'update', action.errors)),

    [CATEGORY_CREATE_REQUEST]: (state, action) => request(state),
    [CATEGORY_CREATE_SUCCESS]: (state, action) => merge(success(state), formErrors(state, 'create', {})),
    [CATEGORY_CREATE_FAILURE]: (state, action) => merge(fail(state), formErrors(state, 'create', action.errors)),

    [MARK_CATEGORY]: (state, action) => merge(state, formValues(state, 'update', action.category)),

    [TOGGLE_CREATE_CATEGORY_FORM]: (state, action) => merge(state, formToggle(state, 'create')),
    [TOGGLE_EDIT_CATEGORY_FORM]: (state, action) => merge(state, formToggle(state, 'update'))
});

