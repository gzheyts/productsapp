import React from 'react';
import PropTypes from 'prop-types';
import BootstrapTable from 'react-bootstrap-table-next';
import overlayFactory from 'react-bootstrap-table2-overlay';
import paginationFactory from "../table/paginationFactory";
import {btnDelete, btnEdit, btnNew} from "./buttons";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const RemoteTable = props => {
    return (
        <BootstrapTable
            loading={props.isLoading}
            remote
            overlay={overlayFactory({spinner: true, background: 'rgba(74, 82, 69, 0.6)'})}
            hover
            bordered={false} keyField="id"
            data={props.content}
            columns={[
                ...props.columns
                , {
                    dataField: 'editAction',
                    text: '',
                    formatter: (cell) => btnEdit,
                    headerFormatter: () => btnNew(props.handleCreate)
                }
                , {dataField: 'deleteAction', text: '', formatter: (cell) => btnDelete}
            ]}
            pagination={paginationFactory(props.total, props.page, props.pageSize, props.onPageChange)}
            onTableChange={() => {
            }}
            rowEvents={{
                onClick: (evt, row, rowInd) => {
                    let target = evt.target || evt.srcElement;
                    evt.preventDefault();
                    if (target.tagName.toLowerCase() !== "button") {
                        return;
                    }
                    if (target.classList.contains('btnDelete')) {
                        props.handleDelete(evt, row, rowInd)
                    } else if (target.classList.contains('btnUpdate')) {
                        props.handleUpdate(evt, row, rowInd);
                    }
                }
            }}/>
    );
};

RemoteTable.propTypes = {
    total: PropTypes.number.isRequired,
    page: PropTypes.number.isRequired,
    pageSize: PropTypes.number.isRequired,

    columns: PropTypes.array.isRequired,
    isLoading: PropTypes.bool.isRequired,
    content: PropTypes.array.isRequired,

    handleCreate: PropTypes.func.isRequired,
    handleDelete: PropTypes.func.isRequired,
    handleUpdate: PropTypes.func.isRequired,

    onPageChange: PropTypes.func.isRequired,

};

export default RemoteTable;

