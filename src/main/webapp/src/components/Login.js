import React from 'react';
import {NavLink} from 'react-router-dom';
import {connect} from 'react-redux';

import * as authActions from '../actions/authActions';
import {Button, Col, Container, Form, FormGroup, Jumbotron, Row} from "reactstrap";
import {PulseLoader} from "react-spinners";
import FormInput from "./forms/controls/formInput";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class Login extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            submitted: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        const {name, value} = e.target;
        this.setState({[name]: value});
    }

    handleSubmit(e) {
        e.preventDefault();
        this.setState({submitted: true});
        const {username, password} = this.state;
        const {dispatch} = this.props;
        if (username && password) {
            dispatch(authActions.queryLogin(username, password));
        }
    }

    render() {
        const {loggingIn} = this.props;
        const {username, password, submitted} = this.state;
        return (
            <Jumbotron>
                <Container>
                    <h2>Login</h2>
                    <Form name="form" onSubmit={this.handleSubmit}>
                        <FormInput
                            type='text'
                            name='username'
                            value={username}
                            invalid={submitted && !username}
                            onChange={this.handleChange}
                            error='Username is required'
                            label='Username'
                            placeholder='Username'
                        />
                        <FormInput
                            type='password'
                            name='password'
                            value={password}
                            invalid={submitted && !password}
                            onChange={this.handleChange}
                            error='Password is required'
                            label='Password'
                            placeholder='Password'
                        />
                        <FormGroup row>
                            <Col sm={{size: 1, offset: 3}}>
                                <Button size="sm" color="success">Login</Button>
                            </Col>
                            <Col sm={1}>
                                <Button size="sm" color="link"><NavLink to="/register">Register</NavLink></Button>
                            </Col>
                        </FormGroup>
                        <Row>
                            <Col md={{size: 3, offset: 3}}>
                                <PulseLoader size={10} color={'#101011'} loading={!!loggingIn}/>
                            </Col>
                        </Row>
                    </Form>
                </Container>
            </Jumbotron>
        );
    }
}

function mapStateToProps(state) {
    const {loggingIn} = state.auth;
    return {
        loggingIn
    };
}

export default connect(mapStateToProps)(Login);
