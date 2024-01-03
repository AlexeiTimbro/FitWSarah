import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import LoginButton from "../../components/authentication/login";
import LogoutButton from "../../components/authentication/logout";
import axios from 'axios';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerPanel.css';

function PersonalTrainerPanel() {

    const {
        isAuthenticated,
        getAccessTokenSilently,
    } = useAuth0();

    const [accessToken, setAccessToken] = useState(null);

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: configData.audience,
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
                    <h1>Trainer Panel</h1>
                    <div className="admin-sections">
                        <Link className="section" to="/trainerAccounts">
                            <h2>Accounts</h2>
                            <p>Current Total: XX Accounts</p>
                            <p>Last Month's Total: XX Accounts</p>
                            <span className="more-options">...</span>
                        </Link>
                        <Link className="section" to="/trainerAppointments">
                            <h2>Appointments</h2>
                            <p>Current Total: XX Appointments</p>
                            <p>Last Month's Total: XX Appointments</p>
                            <span className="more-options">...</span>
                        </Link>
                        <Link className="section" to="/analytics">
                            <h2>Analytics</h2>
                            <p>Current Total: XX Reports</p>
                            <p>Last Month's Total: XX Reports</p>
                            <span className="more-options">...</span>
                        </Link>
                        <Link className="section" to="/feedback">
                            <h2>Feedback</h2>
                            <p>Current Total: XX Submissions</p>
                            <p>Last Month's Total: XX Submissions</p>
                            <span className="more-options">...</span>
                        </Link>
                        <Link className="section" to="/services">
                            <h2>Fitness Services</h2>
                            <p>Current Total: XX Services</p>
                            <p>Last Month's Total: XX Services</p>
                            <span className="more-options">...</span>
                        </Link>
                        <Link className="section" to="/coach-notes">
                            <h2>Coach Notes</h2>
                            <p>Current Total: XX Notes</p>
                            <p>Last Month's Total: XX Notes</p>
                            <span className="more-options">...</span>
                        </Link>
                    </div>
                </div>
            </div>
        </>
    );
}

export default PersonalTrainerPanel;
