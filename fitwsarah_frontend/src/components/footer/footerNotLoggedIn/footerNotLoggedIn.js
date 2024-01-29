import React from 'react';
import './footerNotLoggedIn.css';
import { useLanguage } from '../../../LanguageConfig/LanguageContext';
import { Link } from 'react-router-dom';
function FooterNotLoggedIn() {
  const { changeLanguage } = useLanguage();
  const { t } = useTranslation('footer');

  return (
    <footer className="main-footer">
        <div className="footer-content">
            <a href="/about">{t('aboutMe')}</a>
            <Link to="/contactMe">{t('contactMe')}</Link>
            <p style={{color: 'white'}}>{t('locationInfo')}</p>
            <button onClick={() => changeLanguage('en')}>English</button>
            <button onClick={() => changeLanguage('fr')}>Français</button>
        </div>
        <p className="copy-info">
            {t('copyrightInfo')}
        </p>
    </footer>
  );
}

export default FooterNotLoggedIn;
