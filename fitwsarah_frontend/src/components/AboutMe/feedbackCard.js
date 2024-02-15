import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import ReactStars from 'react-stars'
import {useGetAccessToken} from "../../components/authentication/authUtils";
const FeedbackCard = ({ feedback }) => {
    
    const [userInfo, setUserInfo] = useState('');
    const getAccessToken = useGetAccessToken();

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const accessToken = await getAccessToken();
                const userData = await getAccountByUserId(feedback.userId, accessToken);
                setUserInfo(userData.username);
            } catch (error) {
                console.error("Error fetching user info:", error);
            }
        };

        fetchUserInfo();
    }, [feedback.userId, getAccessToken]);


    const getAccountByUserId = async (userId, accessToken) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts/users/${userId}`, {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + accessToken,
                    "Content-Type": "application/json"
                }
            });

            if (!response.ok) {
                console.error("Response status: " + response.status);
                throw new Error('Network response was not ok ' + response.statusText);
            }

            const userData = await response.json();
            return userData;
        } catch (error) {
            console.error("Error message:", error.message);
        }
    };


    return (
        <div style={{ marginBottom: "15px" }}>
            <h3>{userInfo}</h3>
            <ReactStars count={feedback.stars} size={30} half={false} color1={'#ffd700'} />
            <p><i>"{feedback.content}"</i></p>
        </div>
    );
}
export default FeedbackCard;