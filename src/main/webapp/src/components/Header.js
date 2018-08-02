import React from 'react';
import {
    Collapse,
    DropdownItem,
    DropdownMenu,
    DropdownToggle,
    Nav,
    Navbar,
    NavbarBrand,
    NavbarToggler,
    NavItem,
    NavLink,
    UncontrolledDropdown
} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faSignInAlt, faSignOutAlt, faUserAlt} from '@fortawesome/free-solid-svg-icons'
import {NavLink as RNavLink} from "react-router-dom";
import {connect} from "react-redux";
import * as authActions from "../actions/authActions";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
class Header extends React.Component {
    constructor(props) {
        super(props);
        this.toggle = this.toggle.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.state = {isOpen: false};
    }

    toggle() {
        this.setState({isOpen: !this.state.isOpen});
    }

    handleLogout() {
        this.props.dispatch(authActions.queryLogout())
    }

    render() {
        let {user, loggedIn} = this.props;
        return (
            <div>
                <Navbar color="dark" dark expand="xs">
                    <NavbarBrand tag={RNavLink} to="/" href="/">ProductsApp</NavbarBrand>
                    <NavbarToggler onClick={this.toggle}/>
                    {loggedIn && <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="navbar-right" navbar>
                            <NavItem>
                                <NavLink tag={RNavLink} to="/products" activeClassName="active">Products</NavLink>
                            </NavItem>
                            <NavItem>
                                <NavLink tag={RNavLink} to="/categories" activeClassName="active">Categories</NavLink>
                            </NavItem>
                        </Nav>
                        <Nav className="ml-auto" navbar>
                            <UncontrolledDropdown nav inNavbar>
                                <DropdownToggle nav caret>
                                    <FontAwesomeIcon icon={faUserAlt}/> {user.username}
                                </DropdownToggle>
                                <DropdownMenu right>
                                    <DropdownItem onClick={this.handleLogout}>
                                        <FontAwesomeIcon icon={faSignOutAlt} color="dark" size="sm"/> Logout
                                    </DropdownItem>
                                </DropdownMenu>
                            </UncontrolledDropdown>
                        </Nav>
                    </Collapse>}
                    {!loggedIn &&
                    <Nav className="ml-auto" navbar>
                        <NavItem>
                            <NavLink tag={RNavLink} to="/login"><FontAwesomeIcon icon={faSignInAlt}/> Login</NavLink>
                        </NavItem>
                    </Nav>
                    }
                </Navbar>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    let {auth: {user, loggedIn}} = state;
    return {
        user,
        loggedIn
    };
};
export default connect(mapStateToProps)(Header);