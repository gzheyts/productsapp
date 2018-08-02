import * as categoryService from '../service/categoryService';
import {
    CATEGORY_CREATE_FAILURE,
    CATEGORY_CREATE_REQUEST,
    CATEGORY_CREATE_SUCCESS,
    CATEGORY_DELETE_FAILURE,
    CATEGORY_DELETE_REQUEST,
    CATEGORY_DELETE_SUCCESS,
    CATEGORY_PAGE_FAILURE,
    CATEGORY_PAGE_REQUEST,
    CATEGORY_PAGE_SUCCESS,
    CATEGORY_UPDATE_FAILURE,
    CATEGORY_UPDATE_REQUEST,
    CATEGORY_UPDATE_SUCCESS,
    MARK_CATEGORY,
    TOGGLE_CREATE_CATEGORY_FORM,
    TOGGLE_EDIT_CATEGORY_FORM
} from './actionTypes';
import actionCreator from "../helpers/actionCreator";
import {API_CALL} from "../helpers/callApiMiddleware";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const markCategoryForUpdate = actionCreator(MARK_CATEGORY, 'category');

export const loadCategoryPage = (page, size) => ({
    types: [CATEGORY_PAGE_REQUEST, CATEGORY_PAGE_SUCCESS, CATEGORY_PAGE_FAILURE],
    [API_CALL]: () => categoryService.queryCategoryPage({params: {page, size}}),
    payload: {page, size}
});

export const deleteCategory = (id) => ({
    types: [CATEGORY_DELETE_REQUEST, CATEGORY_DELETE_SUCCESS, CATEGORY_DELETE_FAILURE],
    [API_CALL]: () => categoryService.queryCategoryDelete(id),
    payload: {id},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState().categories;
            dispatch(loadCategoryPage(page, pageSize));
        },
        onFailure: (dispatch, error, getState) => {
        }
    }
});

export const createCategory = (category) => ({
    types: [CATEGORY_CREATE_REQUEST, CATEGORY_CREATE_SUCCESS, CATEGORY_CREATE_FAILURE],
    [API_CALL]: () => categoryService.queryCategoryCreate(category),
    payload: {category},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState().categories;
            dispatch(loadCategoryPage(page, pageSize));
        }
    }
});

export const updateCategory = (category) => ({
    types: [CATEGORY_UPDATE_REQUEST, CATEGORY_UPDATE_SUCCESS, CATEGORY_UPDATE_FAILURE],
    [API_CALL]: () => categoryService.queryCategoryUpdate(category),
    payload: {category},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState().categories;
            dispatch(loadCategoryPage(page, pageSize));
        }
    }
});

export const toggleEditCategoryForm = actionCreator(TOGGLE_EDIT_CATEGORY_FORM);
export const toggleCreateCategoryForm = actionCreator(TOGGLE_CREATE_CATEGORY_FORM);
