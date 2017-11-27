import { CREATE_ORDER, ENABLE_FORM } from '../actions';

export default function(state = null, action) {
  switch (action.type) {
    case CREATE_ORDER:
      return action.payload.data;
    case ENABLE_FORM:
      return null;
    default:
      return state;
  }
}
