import {REGISTER_FAILURE, REGISTER_REQUEST, REGISTER_SUCCESS} from '../actions/actionTypes';
import createReducer from "../helpers/createReducer";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const registration = createReducer({}, {
    [REGISTER_REQUEST]: (state, action) => ({registering: true})
    , [REGISTER_SUCCESS]: (state, action) => ({registering: false})
    , [REGISTER_FAILURE]: (state, action) => ({registering: false})
});