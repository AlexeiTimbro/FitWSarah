import React from 'react';
import './navLoggedIn.css'; 
import { useAuth0 } from '@auth0/auth0-react';
import logo from './image-24.png';
import { Link } from 'react-router-dom';
import { ROLES } from '../../authentication/roles';
import RoleBasedLink from '../../authentication/RoleBasedLink';


function NavLoggedIn() {
  const { logout, user } = useAuth0();

  return (
      <header>
        <nav className="navbar-container">
          <div className="left-links">
            <Link to="/"><img src={logo} alt="app logo" /></Link>
            <a href="#">About</a>
            <a href="#">Contact Me</a>
            <Link to="/profile">Profile</Link>
          </div>
  <div className="right-links">
    <RoleBasedLink className="adminPanel-button" user={user} role={ROLES.PERSONAL_TRAINER} to="/trainerPanel">Trainer Panel</RoleBasedLink>
    <RoleBasedLink className="adminPanel-button" user={user} role={ROLES.ADMIN} to="/adminPanel">Admin Panel</RoleBasedLink>
    <button className="signup-button" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
      Log Out
  </button>    
  </div>
</nav>
</header>
  );
}

export default NavLoggedIn;