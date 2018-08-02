import * as productService from '../service/productService';
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
} from './actionTypes';
import {API_CALL} from "../helpers/callApiMiddleware";
import actionCreator from "../helpers/actionCreator";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const markProductForUpdate = actionCreator(MARK_PRODUCT, 'product');

export const loadProductPage = (page, size) => ({
    types: [PRODUCT_PAGE_REQUEST, PRODUCT_PAGE_SUCCESS, PRODUCT_PAGE_FAILURE],
    [API_CALL]: () => productService.queryProductPage({params: {page, size, sort: "whenCreated"}}),
    payload: {page, size}
});

export const deleteProduct = (id) => ({
    types: [PRODUCT_DELETE_REQUEST, PRODUCT_DELETE_SUCCESS, PRODUCT_DELETE_FAILURE],
    [API_CALL]: () => productService.queryProductDelete(id),
    payload: {id},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState();
            dispatch(loadProductPage(page, pageSize));
        },
        onFailure: (dispatch, error, getState) => {
        }
    }
});

export const createProduct = (product) => ({
    types: [PRODUCT_CREATE_REQUEST, PRODUCT_CREATE_SUCCESS, PRODUCT_CREATE_FAILURE],
    [API_CALL]: () => productService.queryProductCreate(product),
    payload: {product},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState().products;
            dispatch(loadProductPage(page, pageSize));
        }
    }
});

export const updateProduct = (product) => ({
    types: [PRODUCT_UPDATE_REQUEST, PRODUCT_UPDATE_SUCCESS, PRODUCT_UPDATE_FAILURE],
    [API_CALL]: () => productService.queryProductUpdate(product),
    payload: {product},
    callbacks: {
        onSuccess: (dispatch, response, getState) => {
            let {page, pageSize} = getState().products;
            dispatch(loadProductPage(page, pageSize));
        }
    }
});
export const toggleEditProductForm = actionCreator(TOGGLE_EDIT_PRODUCT_FORM);
export const toggleCreateProductForm = actionCreator(TOGGLE_CREATE_PRODUCT_FORM);
