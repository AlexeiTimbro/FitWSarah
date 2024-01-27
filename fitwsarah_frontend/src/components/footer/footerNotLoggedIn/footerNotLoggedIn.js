import React from 'react';
import './footerNotLoggedIn.css'; 
import { useLanguage } from '../../../LanguageContext/LanguageContext'; 
import { useTranslation } from 'react-i18next';

function FooterNotLoggedIn() {
  const { changeLanguage } = useLanguage();
  const { t } = useTranslation();

  return (
    <footer className="main-footer">
        <div className="footer-content">
            <a href="/about">{t('aboutMe')}</a>
            <a href="/contact">{t('contactMe')}</a>
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
