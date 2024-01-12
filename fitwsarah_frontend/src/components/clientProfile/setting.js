// Settings.js
import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import { Container, Row, Col } from 'react-bootstrap';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import {useGetAccessToken} from "../authentication/authUtils";
import configData from "../../config.json";
import Sidebar from "../../views/ProfilePage/SideBar";
function Settings() {
    const { isAuthenticated, user } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);
    const [profilePicUrl, setProfilePicUrl] = useState('');

    useEffect(() => {
        if (user && user.picture) {
            setProfilePicUrl(user.picture);
        }
    }, [user]);

    const getAccessToken = useGetAccessToken();

    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            if (token) setAccessToken(token);
        };

        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (user && user.sub) {
            getAccountByUserId(user.sub);
        }
        if (accessToken) {
            getAppointmentsByAccountId("dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2");
        }
    }, [user]);

    const getAccountByUserId = async (userId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/accounts/${userId}`, {
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
            console.error("Error fetching account details for userId", userId, ":", error);
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

    console.log(user);
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

                    <h1>Account Settings</h1>
                    <Form>
                        <Form.Group controlId="formUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                        </Form.Group>

                        <Form.Group controlId="formFirstName">
                            <Form.Label>First Name</Form.Label>
                            <Form.Control type="text" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                        </Form.Group>

                        <Form.Group controlId="formLastName">
                            <Form.Label>Last Name</Form.Label>
                            <Form.Control type="text" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                        </Form.Group>

                        <Form.Group controlId="formEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
                        </Form.Group>

                        <Button variant="primary" onClick={handleUpdate}>
                            Update
                        </Button>
                    </Form>

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


export default Settings;


