import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Field, reduxForm } from 'redux-form';

import { createOrder, enableForm } from '../actions';

class CreateOrder extends Component {
  onSubmit(form) {
    form.user_id = '1';
    if (!form.station_id) {
      form.station_id = '111';
    }
    this.props.createOrder(form);
    this.props.reset();
  }

  render() {
    if (this.props.order != null) {
      setTimeout(() => this.props.enableForm(), 5000);
    }
    console.log(this.props.order);
    const { handleSubmit, pristine, submitting } = this.props;
    return (
      <div className="card" style={{ padding: '10px', height: '200px' }}>
        <h1>Create New Order</h1>
        <form
          onSubmit={handleSubmit(this.onSubmit.bind(this))}
          className="input-group"
          style={{ marginTop: '30px' }}
        >
          <Field
            placeholder="Enter a station"
            name="station_id"
            className="form-control"
            component="input"
            type="text"
          />
          <button
            type="submit"
            className="btn btn-primary"
            disabled={this.props.order || pristine || submitting}
          >
            Submit
          </button>
        </form>
        {this.props.order && (
          <span style={{ color: 'red' }}>{this.props.order.message}</span>
        )}
      </div>
    );
  }
}

function mapStateToProps({ order }) {
  return {
    order
  };
}

export default connect(mapStateToProps, { createOrder, enableForm })(
  reduxForm({
    form: 'createorder'
  })(CreateOrder)
);
