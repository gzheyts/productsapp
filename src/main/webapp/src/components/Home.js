import React from 'react';
import {Container, Jumbotron} from "reactstrap";

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
const Home = () => {
    return (
        <div>
            <Jumbotron fluid className="text-center">
                <Container fluid>
                    <h1 className="display-3">ProductsApp</h1>
                    <p className="lead">Reactjs + Spring Boot demo</p>
                </Container>
            </Jumbotron>
        </div>);
};

export default Home;
