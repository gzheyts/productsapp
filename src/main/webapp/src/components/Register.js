import React from 'react';
import {Link} from 'react-router-dom';
import {connect} from 'react-redux';

import * as userActions from '../actions/authActions';
import {Button, Col, Container, Form, FormGroup, Jumbotron, Row} from "reactstrap";

import {PulseLoader} from 'react-spinners';
import FormInput from "./forms/controls/formInput";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class Register extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            user: {
                username: '',
                password: ''
            },
            submitted: false
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const {name, value} = event.target;
        const {user} = this.state;
        this.setState({
            user: {
                ...user,
                [name]: value
            }
        });
    }

    handleSubmit(event) {
        event.preventDefault();

        this.setState({submitted: true});
        const {user} = this.state;
        const {dispatch} = this.props;
        if (user.username && user.password) {
            dispatch(userActions.queryRegister(user));
        }
    }

    render() {
        const {registering} = this.props;
        const {user, submitted} = this.state;
        return (
            <Jumbotron fluid>
                <Container>
                    <h2>Register</h2>
                    <Form name="form" onSubmit={this.handleSubmit}>
                        <FormInput
                            type='text'
                            name='username'
                            value={user.username}
                            invalid={submitted && !user.username}
                            onChange={this.handleChange}
                            error='Username is required'
                            label='Username'
                            placeholder='Username'
                        />
                        <FormInput
                            type='password'
                            name='password'
                            value={user.password}
                            invalid={submitted && !user.password}
                            onChange={this.handleChange}
                            error='Password is required'
                            label='Password'
                            placeholder='Password'
                        />

                        <FormGroup row>
                            <Col sm={{size: 1, offset: 3}}>
                                <Button size="sm" color="success">Register</Button>
                            </Col>
                            <Col sm={1}>
                                <Button size="sm" color="link"><Link to="/login">Cancel</Link></Button>
                            </Col>
                        </FormGroup>
                        <Row>
                            <Col md={{size: 3, offset: 3}}>
                                <PulseLoader
                                    size={10}
                                    color={'#101011'}
                                    loading={!!registering}
                                />
                            </Col>
                        </Row>
                    </Form>
                </Container>
            </Jumbotron>
        );
    }
}

function mapStateToProps(state) {
    const {registering} = state.registration;
    return {
        registering
    };
}

export default connect(mapStateToProps)(Register);
