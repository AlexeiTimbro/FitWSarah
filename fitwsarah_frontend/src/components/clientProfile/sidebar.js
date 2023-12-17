import React from 'react';
import { Link } from 'react-router-dom';
import "../../css/style.css";

function SideBar() {
    return (
        <div className="profile-container">
            <div className="profile-sidebar">
                <ul>
                    <li>
                        <Link to="/settings">Profile Settings</Link>
                    </li>
                    <li>
                        <Link to="/invoices">Dark mode</Link>
                    </li>
                    <li>
                        <Link to="/appointments"> appointments</Link>
                    </li>
                    <li>
                        <Link to="/sign out">Sign Out</Link>
                    </li>
                </ul>
            </div>
            {/* Add the rest of your content here */}
        </div>
    );
}

export default SideBar;