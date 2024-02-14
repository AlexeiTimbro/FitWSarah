import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminPanel.css';
import { useTranslation } from "react-i18next";
import { MdManageAccounts, MdAnalytics, MdFeedback, MdSportsMartialArts  } from "react-icons/md";
import { AiOutlineSchedule } from "react-icons/ai";
import { LiaFileInvoiceSolid } from "react-icons/lia";
import { SlSpeech } from "react-icons/sl";

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
              <div className="section-icon">
                <MdManageAccounts />
              </div>
              <h2>{t('adminPanelaccounts')}</h2>
            </Link>
            <Link className="section" to="/adminAppointments">
              <div className="section-icon">
                <AiOutlineSchedule />
              </div>
              <h2>{t('adminPanelAppointments')}</h2>
            </Link>
            <Link className="section" to="/adminInvoices">
              <div className="section-icon">
                <LiaFileInvoiceSolid />
              </div>
              <h2>{t('adminPanelInvoices')}</h2>
            </Link>
            <Link className="section" to="/adminCoachNotes">
              <div className="section-icon">
                <SlSpeech />
              </div>
              <h2>{t('adminPanelCoachNotes')}</h2>
            </Link>
            <Link className="section" to="/analytics">
              <div className="section-icon">
                <MdAnalytics />
              </div>
              <h2>{t('adminPanelAnalytics')}</h2>
            </Link>
            <Link className="section" to="/adminFeedback">
              <div className="section-icon">
                <MdFeedback />
              </div>
              <h2>{t('adminPanelFeedback')}</h2>
            </Link>
            <Link className="section" to="/services">
              <div className="section-icon">
                <MdSportsMartialArts />
              </div>
              <h2>{t('adminPanelServices')}</h2>
            </Link>
          </div>
        </div>
      </div>
    </>
    );
}

export default AdminPanel;
