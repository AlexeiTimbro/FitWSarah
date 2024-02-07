import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminPanel.css';
import { useTranslation } from "react-i18next";

function AdminPanel() {

    const {
        isAuthenticated,
        getAccessTokenSilently,
      } = useAuth0();

    const [accessToken, setAccessToken] = useState(null);

    const { t } = useTranslation('adminPanel');

    useEffect(() => {
        if (isAuthenticated) {
          const getAccessToken = async () => {
            try {
              const token = await getAccessTokenSilently({
                audience: process.env.REACT_APP_AUTH0_AUDIENCE,
                scope: configData.scope,
              });
              setAccessToken(token);
            } catch (e) {
              console.error(e.message);
            }
          };
          getAccessToken();
        }
      }, [getAccessTokenSilently, isAuthenticated]);

    useEffect(() => {
        if (accessToken) {
        }
    }, [accessToken]);


    
    

    return (
      

      <>
      {!isAuthenticated && <NavNotLoggedIn />}
      {isAuthenticated && <NavLoggedIn />}
      <div className="admin-panel-container">
        <div className="admin-container">
          <h1>{t('adminPanel')}</h1>
          <div className="admin-sections">
            <Link className="section" to="/adminAccounts">
              <h2>{t('adminPanelaccounts')}</h2>
              <p>{t('currentTotalAccounts', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalAccounts', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/adminAppointments">
              <h2>{t('adminPanelAppointments')}</h2>
              <p>{t('currentTotalAppointments', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalAppointments', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/adminInvoices">
              <h2>{t('adminPanelInvoices')}</h2>
              <p>{t('currentTotalInvoices', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalInvoices', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/coachNote">
              <h2>{t('adminPanelCoachNotes')}</h2>
              <p>{t('currentTotalCoachNotes', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalCoachNotes', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/analytics">
              <h2>{t('adminPanelAnalytics')}</h2>
              <p>{t('currentTotalAnalytics', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalAnalytics', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/adminFeedback">
              <h2>{t('adminPanelFeedback')}</h2>
              <p>{t('currentTotalFeedback', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalFeedback', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>
            <Link className="section" to="/services">
              <h2>{t('adminPanelServices')}</h2>
              <p>{t('currentTotalServices', { count: 'XX' })}</p>
              <p>{t('lastMonthTotalServices', { count: 'XX' })}</p>
              <span className="more-options">...</span>
            </Link>

          </div>
        </div>
      </div>
    </>
    );
}

export default AdminPanel;
