import React from 'react';
import './navNotLoggedIn.css'; 
import logo from './image-24.png';
import { useAuth0 } from '@auth0/auth0-react';
import RegisterButton from '../../authentication/register';
import { Link } from 'react-router-dom';
import { useLanguage } from "../../../LanguageContext/LanguageContext.js";
import { useTranslation } from "react-i18next";


function NavNotLoggedIn() {

  const { loginWithRedirect } = useAuth0();
  const { language } = useLanguage();
  const { t } = useTranslation();

  return (
    <header>
      <nav className="navbar-container">

        <div className="left-links">
        <Link to="/"><img src={logo} alt="app logo"/></Link>
        <a href="#">{t('aboutMe')}</a>
          <a href="#">{t('contactMe')}</a>
        </div>

        <div className="right-links">
          <button className="login-button" onClick={() => loginWithRedirect()}>Log In</button>;
          <RegisterButton/>
        </div>
      </nav>
      <div className="divider"></div>
    </header>
  );
}

export default NavNotLoggedIn;