import registerServiceWorker from './registerServiceWorker';
import React from 'react';
import {render} from 'react-dom';
import {Provider} from 'react-redux';
import {store} from './store/store';
import 'bootstrap/dist/css/bootstrap.css'
import ProductsApp from './ProductsApp';

/**
 * @author Georgy Zheyts <gzheyts@gmail.com>
 */
render(
    <Provider store={store}>
        <ProductsApp/>
    </Provider>,
    document.getElementById('root'));
registerServiceWorker();
