import React from 'react';
import {Button} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faEdit, faPlus, faTrashAlt} from '@fortawesome/free-solid-svg-icons'

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
export const btnNew = (onClick) =>
    <Button outline size="sm" color="success" className="btnNew"
            onClick={onClick}> <FontAwesomeIcon icon={faPlus}/> New</Button>;

export const btnEdit = <Button outline size="sm" color="primary" className="btnUpdate">
    <FontAwesomeIcon icon={faEdit}/> Edit</Button>;

export const btnDelete = <Button outline size="sm" color="danger" className="btnDelete">
    <FontAwesomeIcon icon={faTrashAlt}/> Delete</Button>;
