import React, {Component} from 'react';
import {Route, Router} from "react-router-dom";
import Categories from "./components/Categories";
import Products from "./components/Products";
import AppHeader from "./components/Header";
import {Alert} from "reactstrap";
import {connect} from "react-redux";
import Login from "./components/Login";
import Register from "./components/Register";
import {PrivateRoute} from "./components/route/privateRoute";
import * as alertActions from "./actions/alertActions";

import {history} from './helpers/history';
import Home from "./components/Home";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class ProductsApp extends Component {
    constructor(props) {
        super(props);

        const {dispatch} = this.props;
        history.listen((location, action) => {
            dispatch(alertActions.clear());
        });
    }

    render() {
        const {alert} = this.props;
        return (
            <div>
                <Router history={history}>
                    <div>
                        <AppHeader/>
                        <Alert color={alert.type} isOpen={!!alert.message}>{alert.message}</Alert>
                        <div>
                            <Route exact path="/" component={Home}/>
                            <PrivateRoute path="/products" component={Products}/>
                            <PrivateRoute path="/categories" component={Categories}/>
                            <Route path="/login" component={Login}/>
                            <Route path="/register" component={Register}/>
                        </div>
                    </div>
                </Router>
            </div>
        );
    }
}

function mapStateToProps(state) {
    const {alert} = state;
    return {alert};
}

export default connect(mapStateToProps)(ProductsApp);
