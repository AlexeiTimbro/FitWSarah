import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import { Button, Form } from 'react-bootstrap';

import configData from "../../config.json";
import { useTranslation } from "react-i18next";

function Settings() {
    const { isAuthenticated, getAccessTokenSilently, user } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [ profile,setProfile] = useState(null);
    const [ profileUrl, setProfilePicUrl] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [city, setCity] = useState('');
    const [setUpdateConfirmation] = useState('');
    const [showModal, setShowModal] = useState(false);
    const { t } = useTranslation('settings');

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

    function extractBeforePipe(userId) {
        const parts = userId.split('|');
        if (parts.length === 2) {
            return parts[0];
        } else {
            return userId;
        }
    }

    return (
        <div>
            <h1>{t('accountSettings')}</h1>
            <Form>
                <Form.Group controlId="formUsername">
                    <Form.Label>{t('username')}</Form.Label>
                    <Form.Control type="text" value={username} onChange={(e) => setUsername(e.target.value)} disabled={extractBeforePipe(user.sub) != 'auth0'} />
                </Form.Group>

                <Form.Group controlId="formEmail">
                    <Form.Label>{t('email')}</Form.Label>
                    <Form.Control type="text" value={email} onChange={(e) => setEmail(e.target.value)} disabled={extractBeforePipe(user.sub) != 'auth0'} />
                </Form.Group>

                <Form.Group controlId="formCity" style={{ marginBottom: '10px' }}>
                    <Form.Label>{t('city')}</Form.Label>
                    <Form.Control type="text" value={city} onChange={(e) => setCity(e.target.value)} />
                </Form.Group>

                <Button variant="primary" onClick={() => handleUpdate(extractAfterPipe(user.sub))} style={{ marginTop: '20px' }}> {/* Add margin to the top */}
                    {t('update')}
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
                    <p>{t('updateSuccess')}</p>
                    <p>{t('username')}: {username}</p>
                    <p>{t('email')}: {email}</p>
                    <p>{t('city')}: {city}</p>
                    <button onClick={() => setShowModal(false)}>{t('appointmentClose')}</button>
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
    );
}

export default Settings;


