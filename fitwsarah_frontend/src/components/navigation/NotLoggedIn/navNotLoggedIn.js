import React from 'react';
import './navNotLoggedIn.css'; 
import logo from './image-24.png';

function NavNotLoggedIn() {
  return (
    <header>
      <nav className="navbar-container">

        <div className="left-links">
        <img src={logo} alt="app logo"/>
        <a href="#">About</a>
          <a href="#">Contact Me</a>
        </div>

        <div className="right-links">
          <button className="login-button">Login</button>
          <button className="signup-button">Sign up</button>
        </div>
      </nav>
      <div className="divider"></div>
    </header>
  );
}

export default NavNotLoggedIn;