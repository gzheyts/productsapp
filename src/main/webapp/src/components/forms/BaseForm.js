import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Button, Form, Modal, ModalBody, ModalFooter, ModalHeader} from 'reactstrap';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class BaseForm extends Component {
    constructor(props) {
        super(props);
        let {initialValues, localChecks, values} = props;
        this.state = {
            submitted: false,
            values: {
                ...initialValues,
                ...values
            },
            localChecks
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.onChange = this.onChange.bind(this);
        this.validate = this.validate.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        let {errors: {nested: remoteErrors}} = nextProps;
        if (!remoteErrors || Object.keys(remoteErrors).length === 0) {
            this.props.toggle[this.props.mode]()
        }
    }

    handleSubmit(e) {
        e.preventDefault();
        let submitted = true;
        this.setState({submitted});
        if (this.validate(submitted).canSubmit) {
            this.props.onSubmit[this.props.mode](this.state.values);
        }
    }

    onChange(e) {
        const {name, value} = e.target;
        this.setState({values: {...this.state.values, [name]: value}});
    }

    validate(submitted) {
        let fields = {}, canSubmit = true;
        let {localChecks} = this.state;
        let {errors: {nested: remoteErrors = {}}} = this.props;
        for (let fieldKey of Object.keys(localChecks)) {
            let isLocal = localChecks[fieldKey].check(this.state.values[fieldKey]);
            canSubmit = canSubmit && !isLocal;
            fields[fieldKey] = {};
            fields[fieldKey].invalid = (submitted && isLocal) || !!remoteErrors[fieldKey];
            fields[fieldKey].error = (submitted && isLocal) ? localChecks[fieldKey].message : remoteErrors[fieldKey];
        }
        return {canSubmit, fields};
    }

    render() {
        let title = this.props.title[this.props.mode];
        let toggle = this.props.toggle[this.props.mode];
        return (
            <div>
                <Modal isOpen>
                    <ModalHeader toggle={toggle}>{title}</ModalHeader>
                    <ModalBody>
                        <Form onSubmit={this.handleSubmit} id="form">
                            {this.formInner()}
                        </Form>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="secondary" onClick={toggle}>Cancel</Button>
                        <Button color="primary" type="submit" onClick={this.handleSubmit}>Ok</Button>{' '}
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

BaseForm.propTypes = {
    mode: PropTypes.string.isRequired,
    values: PropTypes.object.isRequired,
    initialValues: PropTypes.object.isRequired,
    localChecks: PropTypes.object.isRequired,
    onSubmit: PropTypes.object.isRequired,
    toggle: PropTypes.object.isRequired,
    errors: PropTypes.object
};

export default BaseForm;
