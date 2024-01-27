import React from 'react';
import './Account.css';
import { useTranslation } from "react-i18next";

function Sidebar({ changeSelectedTab }) {

    const { t } = useTranslation('account');

    return (
        <aside className="sidebar">
            <nav className="sidebar-navigation">
                <a name="Appointments" className="sidebar-link" onClick={() => changeSelectedTab("appointments")}>
                    <i className="sidebar-icon vehicles-icon"></i>
                    {t('appointments')}
                </a>
                <a name="Setting" className="sidebar-link" onClick={() => changeSelectedTab("settings")}>
                    <i className="sidebar-icon settings-icon"></i>
                    {t('settings')}
                </a>
                <a name="Notes" className="sidebar-link" onClick={() => changeSelectedTab("coachnotes")}>
                    <i className="sidebar-icon appointments-icon"></i>
                    {t('coachNotes')}
                </a>
                <a name="invoices" className="sidebar-link" onClick={() => changeSelectedTab("invoices")}>
                    <i className="sidebar-icon invoices-icon"></i>
                    {t('invoices')}
                </a>
            </nav>
        </aside>
    );
}

export default Sidebar;