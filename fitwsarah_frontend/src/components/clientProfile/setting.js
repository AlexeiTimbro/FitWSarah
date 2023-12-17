// Settings.js
import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import { Container, Row, Col } from 'react-bootstrap';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
const Settings = () => {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [profile, setProfile] = useState(null);

    useEffect(() => {
        const fetchProfile = async () => {
            if (isAuthenticated) {
                try {
                    const accessToken = await getAccessTokenSilently();
                    const response = await fetch('http://localhost:8080/api/v1/accounts/uuid-acc2', {
                        method: 'GET',
                        headers: {
                            Authorization: `Bearer ${accessToken}`,
                            'Content-Type': 'application/json',
                        },
                    });

                    if (!response.ok) {
                        throw new Error(`Network response was not ok ${response.statusText}`);
                    }

                    const data = await response.json();
                    setProfile(data);
                } catch (error) {
                    console.error('Error fetching account details:', error);
                }
            }
        };

        fetchProfile();
    }, [getAccessTokenSilently, isAuthenticated]);

    return (
        <div>
            <h2>Settings</h2>
            {profile && (
                <div>
                    <p>Username: {profile.username}</p>
                    <p>Email: {profile.email}</p>
                </div>
            )}
        </div>
    );
};

export default Settings;


