import React, { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Spinner } from 'react-bootstrap';
import configData from '../../config.json';

function AddMemberProfile() {
    const { user, isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [loading, setLoading] = useState(false);

    const fetchData = async () => {
        try {
            const token = await getAccessTokenSilently({
                audience: configData.audience,
                scope: configData.scope,
            });
            setAccessToken(token);
        } catch (error) {
            console.error("Error getting access token: ", error);
        }
    };

    useEffect(() => {  
        if (isAuthenticated) {
            fetchData();
        }
    }, [user, isAuthenticated, getAccessTokenSilently, accessToken]);

    const addMember = async () => {
        try {
            await fetchData();

            if (!accessToken) {
                console.error("Access token not available.");
                return;
            }

            const {sub, nickname, email } = user;
            const dataToSend = { account_Id: sub, username: nickname, email: email};

            const response = await fetch("http://localhost:8080/api/v1/accounts", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(dataToSend)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            console.log(dataToSend);
            console.log(data);
            console.log(accessToken);
        } catch (error) {
            console.error("Error adding member: ", error);
        }
    };

    useEffect(() => {
        if (accessToken) {
            addMember();
            console.log(user)
            console.log("Added Member")
        }
    }, [user, accessToken]);

    return null;
}

export default AddMemberProfile;
