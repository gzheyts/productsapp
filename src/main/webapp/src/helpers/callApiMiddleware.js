import {collectApiErrors} from "./collectApiErrors";
import {queryLogout} from "../actions/authActions";

export const API_CALL = 'API_CALL';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
function callApiMiddleware({dispatch, getState}) {
    return next => action => {
        const {
            types,
            [API_CALL]: apiCall,
            shouldCallApi = () => true,
            payload = {},
            callbacks = {}
        } = action;
        if (!types) {
            return next(action);
        }

        if (!shouldCallApi(getState())) {
            return;
        }
        const [request, success, failure] = types;

        dispatch({...payload, type: request});

        return apiCall().then(
            response => {
                dispatch({...payload, data: response.data, type: success});
                if (callbacks.onSuccess) {
                    callbacks.onSuccess(dispatch, response, getState);
                }
            }
            , error => {
                if(typeof error.response === 'undefined') { // network error
                    localStorage.removeItem('user');
                    return;
                }
                if (error.response.status === 403) {
                    dispatch(queryLogout());
                }
                let apiErrors = collectApiErrors(error);
                dispatch({...payload, errors: apiErrors, type: failure});
                if (callbacks.onFailure) {
                    callbacks.onFailure(dispatch, apiErrors, getState);
                }

            }
        )
    }
}

export default callApiMiddleware;