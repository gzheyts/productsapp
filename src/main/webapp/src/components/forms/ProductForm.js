import React from 'react';
import {connect} from "react-redux";
import {createProduct, toggleCreateProductForm, toggleEditProductForm, updateProduct} from "../../actions/products";
import CategorySelection from "./controls/categorySelection";
import FormInput from "./controls/formInput";
import BaseForm from './BaseForm';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class ProductForm extends BaseForm {

    constructor(props) {
        super(props);
        this.onCategoryChange = this.onCategoryChange.bind(this);
    }

    onCategoryChange(values = []) {
        let [category = {}] = values;
        this.setState({
            values: {
                ...this.state.values
                , categoryId: category.id
                , categoryName: category.name
            }
        });
    }

    formInner() {
        let validation = this.validate(this.state.submitted).fields;
        return (
            <div>
                <FormInput
                    type="text"
                    name="name"
                    value={this.state.values.name}
                    error={validation.name.error}
                    invalid={validation.name.invalid}
                    onChange={this.onChange}
                    label="Product name:"
                    placeholder="Product name"/>
                <FormInput
                    type="text"
                    name="price"
                    value={this.state.values.price}
                    error={validation.price.error}
                    invalid={validation.price.invalid}
                    onChange={this.onChange}
                    label="Price:"
                    placeholder="Product price"/>
                <FormInput
                    type="textarea"
                    name="description"
                    value={this.state.values.description}
                    error={validation.description.error}
                    invalid={validation.description.invalid}
                    onChange={this.onChange}
                    label="Description:"
                    placeholder="Description: "/>
                <CategorySelection
                    name="category"
                    value={this.state.values.categoryId
                        ? [{
                            id: this.state.values.categoryId,
                            name: this.state.values.categoryName
                        }]
                        : []
                    }
                    onChange={this.onCategoryChange}
                    label="Category:"
                    placeholder="..."
                />
            </div>
        );
    }
}

ProductForm.defaultProps = {
    title: {
        update: 'Edit product',
        create: 'Create new product'
    },
    initialValues: {
        name: '',
        price: '',
        description: '',
        categoryId: '',
        categoryName: ''
    },
    localChecks: {
        name: {
            check: (value) => (value.trim().length === 0),
            message: 'Required'

        },
        price: {
            check: (value) => (isNaN(value)),
            message: 'Not a number'
        },
        description: {
            check: (value) => (value.trim().length === 0),
            message: 'Required'
        }
    }
};

const mapStateToProps = (state, props) => {
    let {
        products: {forms: {[props.mode]: {values, errors}}}
    } = state;
    return {values, errors};
};

const mapDispatchToProps = dispatch => ({
    onSubmit: {
        update: (value) => dispatch(updateProduct(value)),
        create: (value) => dispatch(createProduct(value)),
    },
    toggle: {
        update: () => dispatch(toggleEditProductForm()),
        create: () => dispatch(toggleCreateProductForm()),
    },
});

export default connect(mapStateToProps, mapDispatchToProps)(ProductForm);
