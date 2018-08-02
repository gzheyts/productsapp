import React, {Component} from 'react';
import {connect} from 'react-redux';
import {
    deleteProduct,
    loadProductPage,
    markProductForUpdate,
    toggleCreateProductForm,
    toggleEditProductForm,
} from '../actions/products';
import ProductForm from './forms/ProductForm';
import productColumns from './forms/productColumns';
import TableView from './TableView';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class Products extends Component {
    render() {
        return (
            <TableView
                formComponent={ProductForm}
                tableColumns={productColumns}
                {...this.props}
            />
        );
    }
}

const mapStateToProps = (state) => {
    let {products} = state;
    return {
        ...products
        , editFormOpen: products.forms.update.isOpen
        , createFormOpen: products.forms.create.isOpen
    };
};

const mapDispatchToProps = dispatch => ({
    loadPage: (page, size) => dispatch(loadProductPage(page, size)),
    deleteAction: (id) => dispatch(deleteProduct(id)),

    mark: (product) => dispatch(markProductForUpdate(product)),

    toggleCreateForm: () => dispatch(toggleCreateProductForm()),
    toggleEditForm: () => dispatch(toggleEditProductForm())

});

export default connect(mapStateToProps, mapDispatchToProps)(Products)
