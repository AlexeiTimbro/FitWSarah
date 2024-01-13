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


function Profile() {
    const {isAuthenticated,  getAccessTokenSilently, user} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);
    const [profilePicUrl, setProfilePicUrl] = useState('');
    const [accountId, setAccountId] = useState(null);
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
            getAccountByUserId(extractAfterPipe(user.sub));
        }
        if (accessToken) {
            getAppointmentsByAccountId(accountId);
        }
    }, [user]);

    const getAccountByUserId = (userId) => {
        fetch(`http://localhost:8080/api/v1/accounts/users/${userId}`, {
            method: "GET",
            headers: new Headers({
                "Authorization": "Bearer"  + accessToken,
                "Content-Type": "application/json"
            })
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                setProfile(data);
                console.log(data)
            })
            .catch((error) => {
                console.error("Error fetching service details for userId", userId, ":", error);
            });
    };


    const getAppointmentsByAccountId = (accountId) => {
        fetch(`http://localhost:8080/api/v1/appointments/account/${accountId}`, {
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
                    "Error fetching service details for serviceId",
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
                        <button className="edit-profile-btn" onClick={() => console.log('Edit profile clicked')}>
                            Edit Profile
                        </button>
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
