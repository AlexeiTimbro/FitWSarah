import React from 'react';
import './navLoggedIn.css'; 
import { useAuth0 } from '@auth0/auth0-react';
import logo from './image-24.png';
import { Link } from 'react-router-dom';


function NavLoggedIn() {
  const { logout, user } = useAuth0();

  return (
      <header>
        <nav className="navbar-container">
          <div className="left-links">
            <img src={logo} alt="app logo" />
            <a href="#">About</a>
            <a href="#">Contact Me</a>
            <Link to="/profile">Profile</Link>
          </div>
  <div className="right-links">
    {user["https://fitwsarah.com/roles"][0] == "Personal Trainer" && <Link className="adminPanel-button" to="/trainerPanel">Trainer Panel</Link>}
    {user["https://fitwsarah.com/roles"][0] == "Admin" && <Link className="adminPanel-button" to="/adminPanel">Admin Panel</Link>}
    <button className="signup-button" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
      Log Out
  </button>    
  </div>
</nav>
</header>
  );
}

export default NavLoggedIn;