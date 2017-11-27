import React, { Component } from 'react';
import { connect } from 'react-redux';

import { fetchUser } from '../actions';

export class UserInfo extends Component {
  componentDidMount() {
    this.props.fetchUser();
  }

  renderOrderlist() {
    return this.props.user.orders.map(order => {
      return (
        <tr key={order.transaction_id}>
          <th>{order.station_id}</th>
          <th>{order.bike_id}</th>
          <th>{order.grand_total}</th>
          <th>{order.status}</th>
        </tr>
      );
    });
  }

  render() {
    // if (this.props.user == null) {
    //   return <div>Loading...</div>;
    // }
    // console.log(this.props);
    return (
      <div className="card" style={{ padding: '10px' }}>
        <h1>User Info</h1>
        {this.props.user && (
          <div>
            <p style={{ margin: '10px 0 10px 0' }}>
              Welcom back {this.props.user.first_name},{' '}
              {this.props.user.last_name}! You've got{' '}
              <strong>{this.props.user.credit}</strong> credit left.
              <br />Here are your orders:
            </p>
            <div>
              <button
                className="btn btn-primary"
                style={{ margin: '10px 0 10px 0' }}
                onClick={this.props.fetchUser}
              >
                Refresh
              </button>
            </div>
            <table
              className="table table-hover"
              style={{ margin: '10px 0 10px 0' }}
            >
              <thead>
                <tr>
                  <th>Station ID</th>
                  <th>Bike ID</th>
                  <th>Grand Total</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>{this.renderOrderlist()}</tbody>
            </table>
          </div>
        )}
      </div>
    );
  }
}

function mapStateToProps(state) {
  return {
    user: state.user
  };
}

export default connect(mapStateToProps, { fetchUser })(UserInfo);
