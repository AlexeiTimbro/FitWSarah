import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import {Container, Row, Col, Button} from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import { useParams } from "react-router-dom";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import './Account.css';
import Sidebar from "./SideBar";
import AppointmentCard from '../../views/ProfilePage/AppointmentCard';
import Settings from '../../components/clientProfile/setting.js';
import CoachNote from "../../components/CoachNote/CoachNote.js";


function Profile() {
    const {isAuthenticated,  getAccessTokenSilently, user} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);
    const [profilePicUrl, setProfilePicUrl] = useState('');
    const [accountId, setAccountId] = useState(null);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [city, setCity] = useState('');
    const [selectedTab, setSelectedTab] = useState('appointments');

    useEffect(() => {
        if (user && user.picture) {
            setProfilePicUrl(user.picture);
        }
    }, [user]);

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: process.env.REACT_APP_AUTH0_AUDIENCE,
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
            getAccountByUserId(extractAfterPipe(user.sub));
        }
        if (accessToken) {
            getAppointmentsByAccountId("dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2");
        }
    }, [user]);

    const getAccountByUserId = (userId) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts/users/${userId}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json"
            }
        })
            .then((response) => {
                if (!response.ok) {
                    console.error("Response status: " + response.status);
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((userData) => {
                setUsername(userData.username || '');
                setEmail(userData.email || '');
                setCity(userData.city || '');
                //setAccountId(userData.accountId || '');
            })
            .catch((error) => {
                console.error("Error message:", error.message);
            });
    };

    const getAppointmentsByAccountId = (userId) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/account/users/${userId}`, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            },
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
                setAppointments(data);
            })
            .catch((error) => {
                console.error(
                    "Error fetching account details for accountId",
                    ":",
                    error
                );
            });
    };


    function extractAfterPipe(userId) {
        const parts = userId.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return userId;
        }
    }

    function changeSelectedTab(tab) {
        setSelectedTab(tab);
    }


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
                    </div>
                </div>
            </div>
            <div className="profile-page-container">
                <Sidebar changeSelectedTab={changeSelectedTab}/>
                <div className="account-container">
                    {selectedTab === 'appointments' &&
                    <div>
                        <div className="tabs">
                            <button className="tab">Today</button>
                            <button className="tab">Scheduled</button>
                            <button className="tab">Finished</button>
                        </div>
                        <AppointmentCard/>
                    </div>
                    }
                    {
                        selectedTab === 'settings' &&
                        <Settings userId={extractAfterPipe(user.sub)}/>
                    }
                    {
                        selectedTab === 'coachnotes' &&
                        <CoachNote userId={extractAfterPipe(user.sub)}/>
                    }
                </div>
            </div>

            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;