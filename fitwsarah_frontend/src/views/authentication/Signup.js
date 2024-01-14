import React, { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Spinner } from 'react-bootstrap';
import configData from '../../config.json';

function AddMemberProfile() {
    const { user, isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);

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

            const { sub, nickname, email } = user;
            const RegexUsername = nickname.replace(/^[^|]*\|/, '');

            const dataToSend = { userId: sub, username: RegexUsername, email: email };


            //Check if user exists
        const checkResponse = await fetch(`https://${configData.domain}/api/v2/users/${user.sub}`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            },
        });

        if (!checkResponse.ok) {
            throw new Error(`HTTP error during check! Status: ${checkResponse.status}`);
        }

        const checkData = await checkResponse.json();

        if (checkData.exists) {
            console.log("Member already exists");
            return null; 
            // returns null if the member already exists
        }



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
        } catch (error) {
            console.error("Error adding member: ", error);
        }
    };

    useEffect(() => {
        if (accessToken) {
            
            addMember();
            console.log(user)
            console.log("Added Member")
            console.log(`Bearer ${accessToken}`)
        }
    }, [user, accessToken]);

    return null;
}

export default AddMemberProfile;