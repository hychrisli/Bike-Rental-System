import React, { Component } from 'react';

import UserInfo from './UserInfo';
import CreateOrder from './CreateOrder';

const App = () => {
  return (
    <div className="container">
      <h1 className="display-3" style={{ margin: '10px' }}>
        User Dashboard
      </h1>
      <CreateOrder />
      <UserInfo />
    </div>
  );
};

export default App;
