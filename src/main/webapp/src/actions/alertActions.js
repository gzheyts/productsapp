import actionCreator from '../helpers/actionCreator';
import {ALERT_CLEAR, ALERT_ERROR, ALERT_SUCCESS} from './actionTypes';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const success = actionCreator(ALERT_SUCCESS, 'message');
export const error = actionCreator(ALERT_ERROR, 'message');
export const clear = actionCreator(ALERT_CLEAR);