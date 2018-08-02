import React from 'react';

import 'react-bootstrap-typeahead/css/Typeahead.css';
import {AsyncTypeahead} from 'react-bootstrap-typeahead';
import PropTypes from 'prop-types';
import {Col, FormGroup, Label} from "reactstrap";
import * as categoryService from "../../../service/categoryService";

const PER_PAGE = 50;

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class CategorySelection extends React.Component {
    state = {
        isLoading: false,
        options: [],
        query: '',
    };

    _cache = {};
    _handleInputChange = (query) => {
        this.setState({query});
    };
    _handlePagination = (e) => {
        const {query} = this.state;
        const cachedQuery = this._cache[query];
        const page = cachedQuery.page + 1;

        if (cachedQuery.options.length === cachedQuery.total_count) {
            return;
        }

        this.setState({isLoading: true});
        categoryService.queryCategorySearch(query, page).then((resp) => {
            const options = cachedQuery.options.concat(resp.options);
            this._cache[query] = {...cachedQuery, options, page};
            this.setState({isLoading: false, options});
        });
    };
    _handleSearch = (query) => {
        if (this._cache[query]) {
            this.setState({options: this._cache[query].options});
            return;
        }

        this.setState({isLoading: true});
        categoryService.queryCategorySearch(query).then((resp) => {
            this._cache[query] = {...resp, page: 0};
            this.setState({isLoading: false, options: resp.options});
        });
    }

    render() {
        return (
            <FormGroup row>
                <Label sm={{size: 2, offset: 1}} for={this.props.name}>{this.props.label}</Label>
                <Col sm={7}>
                    <AsyncTypeahead
                        {...this.state}
                        labelKey='name'
                        defaultSelected={this.props.value}
                        maxResults={PER_PAGE - 1}
                        minLength={0}
                        onChange={this.props.onChange}
                        onInputChange={this._handleInputChange}
                        onPaginate={this._handlePagination}
                        onSearch={this._handleSearch}
                        paginate
                        placeholder="Search for a category..."
                        renderMenuItemChildren={({id, name}, props) => (
                            <div key={id}>
                                <span>{name}</span>
                            </div>
                        )}
                        useCache={false}
                    />
                </Col>
            </FormGroup>
        );
    }
}

CategorySelection.propTypes = {
    onChange: PropTypes.func.isRequired,
    name: PropTypes.string.isRequired,
    value: PropTypes.array.isRequired,
    label: PropTypes.string.isRequired,
    placeholder: PropTypes.string.isRequired,
};

export default CategorySelection;
