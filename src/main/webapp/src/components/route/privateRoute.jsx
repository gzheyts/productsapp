import React from 'react';
import {Redirect, Route} from 'react-router-dom';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const PrivateRoute = ({component: Component, ...rest}) => (
    <Route {...rest} render={props => {
        return (
            localStorage.getItem('user')
                ? <Component {...props} />
                : <Redirect to={{pathname: '/login', state: {from: props.location}}}/>
        )
    }}/>
);