import { combineReducers } from 'redux';
import { reducer as form } from 'redux-form';

import userReducer from './userReducer';
import orderReducer from './orderReducer';

const rootReducer = combineReducers({
  form,
  user: userReducer,
  order: orderReducer
});

export default rootReducer;
