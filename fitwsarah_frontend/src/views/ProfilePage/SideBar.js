import React from 'react';
import './Account.css';
import { Link } from 'react-router-dom';
function Sidebar() {
    return (
        <aside className="sidebar">

            <nav className="sidebar-navigation">
                <Link className="sidebar-link" to="/settings">
                    <i className="sidebar-icon settings-icon"></i>
                    Profile Settings
                </Link>
                <a href="/invoices" className="sidebar-link">
                    <i className="sidebar-icon vehicles-icon"></i>
                    Dark Mode
                </a>
                <Link className="sidebar-link" to="/coachnotes">
                    <i className="sidebar-icon appointments-icon"></i>
                    Coach Notes
                </Link>
                <a href="/invoices" className="sidebar-link">
                    <i className="sidebar-icon invoices-icon"></i>
                    Invoices
                </a>
            </nav>
        </aside>
    );
}

export default Sidebar;