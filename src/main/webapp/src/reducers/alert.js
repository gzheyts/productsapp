import {ALERT_CLEAR, ALERT_ERROR, ALERT_SUCCESS} from '../actions/actionTypes';
import createReducer from "../helpers/createReducer";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const alert = createReducer({}, {
    [ALERT_SUCCESS]: (state, action) => ({type: 'success', message: action.message})
    , [ALERT_ERROR]: (state, action) => ({type: 'danger', message: action.message})
    , [ALERT_CLEAR]: (state, action) => ({})
});
