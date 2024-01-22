import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import { Container, Row, Col, Button, Form } from 'react-bootstrap';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import { useGetAccessToken } from "../authentication/authUtils";
import configData from "../../config.json";
import Sidebar from "../../views/ProfilePage/SideBar";
import { Alert } from 'flowbite-react';
function Settings() {
    const { isAuthenticated, getAccessTokenSilently, user } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);
    const [profilePicUrl, setProfilePicUrl] = useState('');
    const [accountId, setAccountId] = useState(null);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [city, setCity] = useState('');
    const [updateConfirmation, setUpdateConfirmation] = useState('');
    const [showModal, setShowModal] = useState(false);
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
            fetchUserDataFromDatabase(extractAfterPipe(user.sub));

        }
    }, [user, accessToken]);

    useEffect(() => {
        if (user && user.picture) {
            setProfilePicUrl(user.picture);
        }
    }, [user]);

    const fetchUserDataFromDatabase = (userId) => {
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
            })
            .catch((error) => {
                console.error("Error message:", error.message);
            });
    };


    const handleUpdate = () => {
       /// window.alert(`Profile updated successfully! Username: ${username}, /n , Email: ${email}, /n  City: ${city}`);

        setShowModal(true);

        console.log('Update clicked');
        console.log('New username:', username);
        console.log('New email:', email);



        const updatedProfile = {
            username,
            email,
            city,
        };



        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts/users/${extractAfterPipe(user.sub)}`, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json",
            },
            body: JSON.stringify(updatedProfile),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                if (data && data.success) {
                    setUpdateConfirmation(`Profile updated successfully! Username: ${username}, Email: ${email}, City: ${city}`);
                    setProfile(data.updatedProfile);
                    window.location.reload();
                }
            })
            .catch((error) => {
                console.error("Error message:", error.message);
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
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div className="box">
                <div className="rectangle" />
                <div className="cover-image"></div>
                <div className="profile-content">
                    <div className="profile-image" style={{ backgroundImage: `url(${profilePicUrl})` }}></div>
                    <div className="profile-text">
                        <div className="text-wrapper">Welcome{username}</div>
                    </div>
                </div>
            </div>
            <div className="profile-page-container">
                <Sidebar />
                <div className="account-container">
                    <h1>Account Settings</h1>
                    <Form>
                        <Form.Group controlId="formUsername">
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="text" value={username} onChange={(e) => setUsername(e.target.value)} />
                        </Form.Group>

                        <Form.Group controlId="formEmail">
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="text" value={email} onChange={(e) => setEmail(e.target.value)} />
                        </Form.Group>

                        <Form.Group controlId="formCity" style={{ marginBottom: '10px' }}>
                            <Form.Label>City</Form.Label>
                            <Form.Control type="text" value={city} onChange={(e) => setCity(e.target.value)} />
                        </Form.Group>

                        <Button variant="primary" onClick={() => handleUpdate(extractAfterPipe(user.sub))} style={{ marginTop: '20px' }}> {/* Add margin to the top */}
                            Update
                        </Button>
                    </Form>
                    {showModal && (
                        <div style={{
                            position: 'fixed',
                            top: 0,
                            left: '50%',
                            transform: 'translateX(-50%)',
                            backgroundColor: 'white',
                            padding: '20px',
                            zIndex: 1000,
                            border: '1px solid #ccc',
                            boxShadow: '0px 4px 6px rgba(0,0,0,0.1)'
                        }}>
                            <p>Profile updated successfully!</p>
                            <p>Username: {username}</p>
                            <p>Email: {email}</p>
                            <p>City: {city}</p>
                            <button onClick={() => setShowModal(false)}>Close</button>
                        </div>
                    )}

                   
                    {showModal && (
                        <div style={{
                            position: 'fixed',
                            top: 0,
                            left: 0,
                            right: 0,
                            bottom: 0,
                            backgroundColor: 'rgba(0,0,0,0.3)',
                            zIndex: 999
                        }} onClick={() => setShowModal(false)} />
                    )}
                </div>
            </div>
            <FooterNotLoggedIn />
        </div>
    );
}

export default Settings;


