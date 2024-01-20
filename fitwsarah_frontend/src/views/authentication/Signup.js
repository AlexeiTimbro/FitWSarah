
import React, { useState, useEffect, useCallback } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Spinner } from 'react-bootstrap';
import configData from '../../config.json';
import { isRole } from "../../components/authentication/authUtils";
import { ROLES } from "../../components/authentication/roles";
//i have to fix sign up issue
function AddMemberProfile() {
    const {user, isAuthenticated, getAccessTokenSilently} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    useEffect(() => {
        if (isAuthenticated) {
            localStorage.setItem("userData", JSON.stringify(user));
            fetchData();
        }
    }, [user, isAuthenticated, getAccessTokenSilently, accessToken]);

    const fetchData = async () => {
        try {
            const token = await getAccessTokenSilently({
                audience: process.env.REACT_APP_AUTH0_AUDIENCE,
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

    function extractAfterPipe(originalString) {
        const parts = originalString.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return originalString;
        }
    }


    const addMember = async () => {
        try {
            await fetchData();

            if (!accessToken) {
                console.error("Access token not available.");
                return;
            }

            const {sub, nickname, email} = user;


            const regexUserId = extractAfterPipe(sub);
            const dataToSend = {
                userId: regexUserId,
                username: nickname,
                email: email,
            };


            const response = await fetch("https://fitwsarah.onrender.com/api/v1/accounts", {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    "Content-Type": "application/json",
                },

                body: JSON.stringify(dataToSend)
            }, [accessToken, fetchData]);


            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            console.log(dataToSend);
            console.log(data);
        } catch (error) {
            console.error("Error adding member: ", error);
        }
    };

    useEffect(() => {
        if (accessToken) {
            const storedUserData = localStorage.getItem("userData");
            if (storedUserData) {
                const userData = JSON.parse(storedUserData);
                console.log(userData);
            }

            addMember();
            isRole(user, ROLES.MEMBER)
            window.location.reload()
            console.log(user);
            console.log("Added Member");
        }
    }, [isAuthenticated, accessToken, user, addMember, isRole]);

}

export default AddMemberProfile;
