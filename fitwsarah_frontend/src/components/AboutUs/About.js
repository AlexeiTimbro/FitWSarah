import React, { useState, useEffect } from 'react';
import './About.css';
import NavNotLoggedIn from "../navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../navigation/loggedIn/navLoggedIn";
import { useAuth0 } from "@auth0/auth0-react";
import { useTranslation } from "react-i18next";
import configData from "../../config.json";
import FooterNotLoggedIn from "../footer/footer"; // Assuming you're using this somewhere within your component

const About = () => {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');

    // If you need to fetch the access token when the component mounts
    useEffect(() => {
        if (isAuthenticated) {
            const fetchAccessToken = async () => {
                const token = await getAccessTokenSilently();
                setAccessToken(token);
            };

            fetchAccessToken().catch(console.error);
        }
    }, [isAuthenticated, getAccessTokenSilently]);

    return (
        <>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
        <div className="admin-panel-container">
            <div className="admin-container">
                <h1>Hello, I am <span className="name-highlight">thom</span></h1>
                <p>A Professional Personal Fitness Trainer</p>
            </div>
        </div>

            <FooterNotLoggedIn/>
            </>
    );
};

export default About;
