import 'babel-polyfill';
import React, { Component } from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';

import App from './components/app';
import reducers from './reducers';
import '../style/style.css';

const store = createStore(reducers, {}, applyMiddleware(thunk));
// const token = localStorage.getItem('token');

// if (token) {
//   store.dispatch({ type: AUTH_USER });
// }

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>,
  document.querySelector('#root')
);
