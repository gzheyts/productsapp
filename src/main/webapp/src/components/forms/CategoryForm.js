import React from 'react';
import PropTypes from 'prop-types';
import {connect} from "react-redux";
import {
    createCategory,
    toggleCreateCategoryForm,
    toggleEditCategoryForm,
    updateCategory
} from "../../actions/categories";
import FormInput from './controls/formInput';
import BaseForm from './BaseForm';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class CategoryForm extends BaseForm {
    formInner() {
        let validation = this.validate(this.state.submitted).fields;
        return (<div>
            <FormInput
                type="text"
                name="name"
                value={this.state.values.name}
                error={validation.name.error}
                invalid={validation.name.invalid}
                onChange={this.onChange}
                label="Category name:"
                placeholder="Category name"/>
            <FormInput
                type="textarea"
                name="description"
                value={this.state.values.description}
                error={validation.description.error}
                invalid={validation.description.invalid}
                onChange={this.onChange}
                label="Description:"
                placeholder="Description: "/>
        </div>);
    }
}

CategoryForm.propTypes = {
    mode: PropTypes.string.isRequired,
    values: PropTypes.object.isRequired,
    onSubmit: PropTypes.object.isRequired,
    toggle: PropTypes.object.isRequired,
    errors: PropTypes.object
};

CategoryForm.defaultProps = {
    title: {update: 'Edit category', create: 'Create new category'},
    localChecks: {
        name: {
            check: (value) => (value.trim().length === 0),
            message: 'Required'

        },
        description: {
            check: (value) => (value.trim().length === 0),
            message: 'Required'
        }
    },
    initialValues: {
        name: '',
        description: ''
    }
};

const mapStateToProps = (state, props) => {
    let {
        categories: {forms: {[props.mode]: {values, errors}}}
    } = state;

    return {values, errors};
};

const mapDispatchToProps = dispatch => ({
    onSubmit: {
        update: (value) => dispatch(updateCategory(value)),
        create: (value) => dispatch(createCategory(value)),
    },
    toggle: {
        update: () => dispatch(toggleEditCategoryForm()),
        create: () => dispatch(toggleCreateCategoryForm()),
    },
});

export default connect(mapStateToProps, mapDispatchToProps)(CategoryForm);
