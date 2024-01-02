import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import { useParams } from "react-router-dom";
import './Account.css';
import Sidebar from "./SideBar";


function Profile() {
    const {isAuthenticated, getAccessTokenSilently, user} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);
    const [profilePicUrl, setProfilePicUrl] = useState(''); // State to store profile picture URL

    useEffect(() => {
        if (user && user.picture) {
            setProfilePicUrl(user.picture); // Set the profile picture URL
        }
    }, [user]);

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: configData.audience,
                        scope: configData.scope,
                    });
                    setAccessToken(token);
                    console.log(user);
                } catch (e) {
                    console.error(e.message);
                }
            };
            getAccessToken();
        }
    }, [getAccessTokenSilently, isAuthenticated]);

    useEffect(() => {
        if (accessToken) {
            getAppointmentsByAccountId("dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2");
        }
    }, [accessToken]);


    useEffect(() => {
        const getUserMetadata = async () => {
            try {
                const userDetailsByIdUrl = `https://${configData.domain}/api/v2/users/${user.sub}`;

                const metadataResponse = await fetch(userDetailsByIdUrl, {
                    headers: {
                        Authorization: `Bearer ${accessToken}`
                    }
                });

                const userDetails = await metadataResponse.json();

                if (userDetails && userDetails.accountId) { // Assuming accountId is directly under userDetails
                    getAccountById(userDetails.accountId);
                    getAppointmentsByAccountId(userDetails.accountId);
                }
            } catch (e) {
                console.log(e.message);
            }
        };

        if (accessToken && isAuthenticated) {
            getUserMetadata();
        }
    }, [accessToken, isAuthenticated, user]);

    const getAccountById = async (accountId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/accounts/${accountId}`, {
                method: "GET",
                headers: new Headers({
                    Authorization: "Bearer " + accessToken,
                    "Content-Type": "application/json",
                }),
            });

            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }

            const data = await response.json();
            setProfile(data);
        } catch (error) {
            console.error("Error fetching account details for accountId", accountId, ":", error);
        }
    };

    const getAppointmentsByAccountId = (accountId) => {
        fetch(`http://localhost:8080/api/v1/appointments/account/${accountId}`, {
            method: "GET",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(
                        "Network response was not ok " + response.statusText
                    );
                }
                return response.json();
            })
            .then((data) => {
                console.log(data)
                setAppointments(data);
            })
            .catch((error) => {
                console.error(
                    "Error fetching service details for serviceId",
                    ":",
                    error
                );
            });
    };


    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div className="box">
                <div className="rectangle"/>
                <div className="cover-image"></div>
                <div className="profile-content">
                    <div className="profile-image" style={{backgroundImage: `url(${profilePicUrl})`}}></div>
                    <div className="profile-text">
                        <div className="text-wrapper">Welcome{user ? ` ${user.nickname}` : ''}</div>
                        <div className="div">Welcome Back!</div>
                    </div>
                </div>
            </div>
            <div className="profile-page-container">
                <Sidebar/>
                <div className="account-container">
                    <div className="tabs">
                        <button className="tab">Today</button>
                        <button className="tab">Scheduled</button>
                        <button className="tab">Finished</button>
                    </div>
                    <ProfileSideBar appointments={appointments} accessToken={accessToken}/>
                </div>
            </div>

            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;
