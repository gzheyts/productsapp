import {applyMiddleware, combineReducers, createStore} from 'redux';
import thunk from 'redux-thunk';
import {createLogger} from 'redux-logger';

import callApiMiddleware from "../helpers/callApiMiddleware";
import {registration} from '../reducers/registration';
import {auth} from '../reducers/auth';
import {alert} from '../reducers/alert';
import {products} from '../reducers/products';
import {categories} from '../reducers/categories';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const rootReducer = combineReducers({
    products,
    categories,
    auth,
    registration,
    alert
});

export const store = createStore(
    rootReducer,
    applyMiddleware(
        callApiMiddleware,
        thunk,
        createLogger()
    )
);
