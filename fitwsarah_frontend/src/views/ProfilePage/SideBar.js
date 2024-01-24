import React from 'react';
import './Account.css';

function Sidebar({ changeSelectedTab }) {
    return (
        <aside className="sidebar">
            <nav className="sidebar-navigation">
                <a name="Appointments" className="sidebar-link" onClick={() => changeSelectedTab("appointments")}>
                    <i className="sidebar-icon vehicles-icon"></i>
                    Appointments
                </a>
                <a name="Setting" className="sidebar-link" onClick={() => changeSelectedTab("settings")}>
                    <i className="sidebar-icon settings-icon"></i>
                    Profile Settings
                </a>
                <a name="Notes" className="sidebar-link" onClick={() => changeSelectedTab("coachnotes")}>
                    <i className="sidebar-icon appointments-icon"></i>
                    Coach Notes
                </a>
                <a name="invoices" className="sidebar-link" onClick={() => changeSelectedTab("invoices")}>
                    <i className="sidebar-icon invoices-icon"></i>
                    Invoices
                </a>
            </nav>
        </aside>
    );
}

export default Sidebar;