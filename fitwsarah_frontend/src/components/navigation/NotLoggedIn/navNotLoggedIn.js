import React from 'react';
import './navNotLoggedIn.css'; 
import logo from './image-24.png';
import { useAuth0 } from '@auth0/auth0-react';
import RegisterButton from '../../authentication/register';
import { Link } from 'react-router-dom';
import { useTranslation } from "react-i18next";


function NavNotLoggedIn() {

  const { loginWithRedirect } = useAuth0();
  const { t } = useTranslation('nav');

  return (
    <header>
      <nav className="navbar-container">

        <div className="left-links">
        <Link to="/"><img src={logo} alt="app logo"/></Link>
          <Link to="/aboutMe">{t('aboutMe')}</Link>
        <Link to="/contactMe">{t('contactMe')}</Link>
        </div>

        <div className="right-links">
          <button className="login-button" onClick={() => loginWithRedirect()}>{t('login')}</button>;
          <RegisterButton/>
        </div>
      </nav>
    </header>
  );
}

export default NavNotLoggedIn;