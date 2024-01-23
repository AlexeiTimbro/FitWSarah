import React from 'react';
import './Account.css';
import { Link } from 'react-router-dom';
function Sidebar({ changeSelectedTab }) {
    return (
        <aside className="sidebar">
            <nav className="sidebar-navigation">
                <a className="sidebar-link" onClick={() => changeSelectedTab("settings")}>
                    <i className="sidebar-icon settings-icon"></i>
                    Profile Settings
                </a>
                <a href="/invoices" className="sidebar-link">
                    <i className="sidebar-icon vehicles-icon"></i>
                    Dark Mode
                </a>
                <a className="sidebar-link" onClick={() => changeSelectedTab("coachnotes")}>
                    <i className="sidebar-icon appointments-icon"></i>
                    Coach Notes
                </a>
                <a href="/invoices" className="sidebar-link">
                    <i className="sidebar-icon invoices-icon"></i>
                    Invoices
                </a>
            </nav>
        </aside>
    );
}

export default Sidebar;