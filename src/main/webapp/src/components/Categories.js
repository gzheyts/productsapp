import React, {Component} from 'react';
import {connect} from 'react-redux';
import {
    deleteCategory,
    loadCategoryPage,
    markCategoryForUpdate,
    toggleCreateCategoryForm,
    toggleEditCategoryForm
} from '../actions/categories';
import TableView from './TableView';
import CategoryForm from "./forms/CategoryForm";
import categoryColumns from "./forms/categoryColumns";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class Categories extends Component {
    render() {
        return (
            <TableView
                formComponent={CategoryForm}
                tableColumns={categoryColumns}
                {...this.props}/>
        );
    }
}

const mapStateToProps = (state) => {
    let {categories, categories: {forms}} = state;
    return {
        ...categories
        , editFormOpen: forms.update.isOpen
        , createFormOpen: forms.create.isOpen
    };
};

const mapDispatchToProps = dispatch => ({
    loadPage: (page, size) => dispatch(loadCategoryPage(page, size)),
    deleteAction: (id) => dispatch(deleteCategory(id)),

    mark: (category) => dispatch(markCategoryForUpdate(category)),

    toggleCreateForm: () => dispatch(toggleCreateCategoryForm()),
    toggleEditForm: () => dispatch(toggleEditCategoryForm())

});

export default connect(mapStateToProps, mapDispatchToProps)(Categories)

