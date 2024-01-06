import React from 'react';
import './Account.css'; // Assuming you create a separate CSS file for the sidebar
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
                <a href="/appointments" className="sidebar-link">
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