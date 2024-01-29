import React from 'react';
import './navLoggedIn.css'; 
import { useAuth0 } from '@auth0/auth0-react';
import logo from './image-24.png';
import { Link } from 'react-router-dom';
import { ROLES } from '../../authentication/roles';
import RoleBasedLink from '../../authentication/RoleBasedLink';
import { useTranslation } from "react-i18next";


function NavLoggedIn() {
  const { logout, user } = useAuth0();
  const { t } = useTranslation('nav');


  return (
      <header>
        <nav className="navbar-container">
          <div className="left-links">
            <Link to="/"><img src={logo} alt="app logo" /></Link>
            <a href="#">{t('aboutMe')}</a>
            <Link to="/contactMe">{t('contactMe')}</Link>
            <Link to="/profile">{t('profile')}</Link>
          </div>
  <div className="right-links">
    <RoleBasedLink className="adminPanel-button" user={user} role={ROLES.PERSONAL_TRAINER} to="/trainerPanel">{t('trainerPanel')}</RoleBasedLink>
    <RoleBasedLink className="adminPanel-button" user={user} role={ROLES.ADMIN} to="/adminPanel">{t('adminPanel')}</RoleBasedLink>
    <button className="signup-button" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
      {t('logout')}
  </button>    
  </div>
</nav>
</header>
  );
}

export default NavLoggedIn;