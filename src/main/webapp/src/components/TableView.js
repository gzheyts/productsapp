import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {Container} from 'reactstrap';
import RemoteTable from '../table/remoteTable';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class TableView extends Component {
    constructor(props) {
        super(props);
        this.handleCreate = this.handleCreate.bind(this);
        this.handleDelete = this.handleDelete.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.onPageChange = this.onPageChange.bind(this);
    }

    componentDidMount() {
        this.props.loadPage(this.props.page, this.props.pageSize);
    }

    handleCreate() {
        this.props.toggleCreateForm();
    }

    handleUpdate(evt, row, rowInd) {
        this.props.mark(this.props.content[rowInd]);
        this.props.toggleEditForm();
    }

    handleDelete(evt, row, rowInd) {
        this.props.deleteAction(this.props.content[rowInd].id);
    }

    onPageChange(page) {
        if (this.props.page !== page) {
            this.props.loadPage(page, this.props.pageSize);
        }
    }

    render() {
        let {formComponent: Form} = this.props;
        return (
            <Container fluid>
                <RemoteTable
                    columns={this.props.tableColumns}
                    content={this.props.content}
                    total={this.props.total}
                    page={this.props.page}
                    pageSize={this.props.pageSize}
                    isLoading={this.props.isLoading}
                    handleCreate={this.handleCreate}
                    handleUpdate={this.handleUpdate}
                    handleDelete={this.handleDelete}
                    onPageChange={this.onPageChange}
                />

                {this.props.createFormOpen && <Form mode='create'/>}
                {this.props.editFormOpen && <Form mode='update'/>}
            </Container>
        );
    }
}

TableView.propTypes = {
    page: PropTypes.number.isRequired,
    pageSize: PropTypes.number.isRequired,
    total: PropTypes.number.isRequired,
    content: PropTypes.array.isRequired,
    isLoading: PropTypes.bool.isRequired,

    editFormOpen: PropTypes.bool.isRequired,
    createFormOpen: PropTypes.bool.isRequired,

    loadPage: PropTypes.func.isRequired,
    deleteAction: PropTypes.func.isRequired,
    mark: PropTypes.func.isRequired,

    toggleCreateForm: PropTypes.func.isRequired,
    toggleEditForm: PropTypes.func.isRequired,

    formComponent: PropTypes.func.isRequired,
    tableColumns: PropTypes.array.isRequired

};

export default TableView;
