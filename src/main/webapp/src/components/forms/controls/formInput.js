import React, {Component} from 'react';
import {Col, FormFeedback, FormGroup, Input, Label} from 'reactstrap';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export default class FormInput extends Component {
    render() {
        const {type, name, value, error, onChange, label, invalid, placeholder} = this.props;
        return (
            <FormGroup row>
                <Label sm={{size: 2, offset: 1}} for={name}>{label}</Label>
                <Col sm={7}>
                    <Input
                        type={type}
                        name={name}
                        value={value}
                        invalid={invalid}
                        onChange={onChange}
                        placeholder={placeholder}
                    />
                    <FormFeedback>{error}</FormFeedback>
                </Col>
            </FormGroup>
        );
    }
}
