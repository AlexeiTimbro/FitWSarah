import React from 'react';
import './navNotLoggedIn.css'; 
import logo from './image-24.png';
import { useAuth0 } from '@auth0/auth0-react';
import RegisterButton from '../../authentication/register';


function NavNotLoggedIn() {

  const { loginWithRedirect } = useAuth0();

  return (
    <header>
      <nav className="navbar-container">

        <div className="left-links">
        <img src={logo} alt="app logo"/>
        <a href="#">About</a>
          <a href="#">Contact Me</a>
        </div>

        <div className="right-links">
          <button className="login-button" onClick={() => loginWithRedirect()}>Log In</button>;
          <RegisterButton></RegisterButton>
        </div>
      </nav>
      <div className="divider"></div>
    </header>
  );
}

export default NavNotLoggedIn;