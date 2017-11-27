import axios from 'axios';

const BASE_URL = 'http://localhost:3000';

export const FETCH_USER = 'fetch_user';
export const fetchUser = () => async dispatch => {
  const res = await axios.get(`${BASE_URL}/user/1`);

  dispatch({
    type: FETCH_USER,
    payload: res
  });
};

export const CREATE_ORDER = 'create_order';
export const createOrder = order => async dispatch => {
  function onSuccess(res) {
    dispatch({
      type: CREATE_ORDER,
      payload: res
    });
  }

  function onError(error) {
    console.log(error);
  }

  try {
    const res = await axios.post(`${BASE_URL}/order`, order);
    onSuccess(res);
  } catch (error) {
    onError(error);
  }
};

export const ENABLE_FORM = 'enable_form';
export const enableForm = () => dispatch => {
  dispatch({
    type: ENABLE_FORM
  });
};
