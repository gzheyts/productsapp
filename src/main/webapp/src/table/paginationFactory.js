import React from 'react';
import paginationFactory from 'react-bootstrap-table2-paginator';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export default (totalSize, page, pageSize, onPageChange) => paginationFactory({
    pageStartIndex: 0,
    paginationSize: 5,
    totalSize: totalSize,
    hideSizePerPage: true,
    page,
    sizePerPage: pageSize,
    hidePageListOnlyOnePage: true,
    firstPageText: '<<',
    prePageText: '<',
    nextPageText: '>',
    lastPageText: '>>',
    showTotal: true,
    sizePerPageList: [{text: '5', value: 5}, {text: '10', value: 10}, {text: 'All', value: 100}],
    onPageChange: onPageChange,
    paginationTotalRenderer: (from, to, size) => ( <span>Showing {from} to {to} of {size} Results</span>)
})
