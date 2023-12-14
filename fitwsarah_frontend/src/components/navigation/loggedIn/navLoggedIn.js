import React from 'react';
import './navLoggedIn.css'; // Importing the CSS file specific to NavLoggedIn
import { useAuth0 } from '@auth0/auth0-react';
import logo from './image-24.png';



function NavLoggedIn() {

  const { logout } = useAuth0();

  return (
    
<header>
<nav className="navbar-container">

  <div className="left-links">
  <img src={logo} alt="app logo"/>
  <a href="#">About</a>
    <a href="#">Contact Me</a>
  </div>

  <div className="right-links">
  <button className="signup-button" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
      Log Out
    </button>    
  </div>
</nav>
<div className="divider"></div>
</header>
  );
}

export default NavLoggedIn;