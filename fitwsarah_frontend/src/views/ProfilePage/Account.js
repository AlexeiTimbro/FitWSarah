import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import './Account.css';
import Sidebar from "./SideBar";
import Settings from '../../components/clientProfile/setting.js';
import CoachNote from "../../components/CoachNote/CoachNote.js";
import Appointment from '../../components/clientProfile/Appointment.js';
import { useTranslation } from "react-i18next";

function Profile() {
    const {isAuthenticated,  getAccessTokenSilently, user} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [profilePicUrl, setProfilePicUrl] = useState('');
    const [accountId, setAccountId] = useState(null);
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [city, setCity] = useState('');
    const [selectedTab, setSelectedTab] = useState('appointments');
    const [appointments, setAppointments] = useState([]);
    const [status, setStatus] = useState('SCHEDULED');
    const { t } = useTranslation('account');

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
    }, [user]);

    useEffect(() => {
        if (accessToken) {
            getAppointmentsByUserId(extractAfterPipe(user.sub), status);
        }
    }, [user, status]);

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

    const getAppointmentsByUserId = (userId, status) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/users/${userId}/status/${status}`, {
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
        .then((data) => {
            console.log(data);
            setAppointments(data);
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

    function changeSelectedTab(tab) {
        setSelectedTab(tab);
    }

    function changeStatus(stat) {
        setStatus(stat);
        console.log(status);
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
                        <div className="text-wrapper">{t('welcome')}{user ? ` ${user.nickname}` : ''}</div>
                    </div>
                </div>
            </div>
            <div className="profile-page-container">
                <Sidebar changeSelectedTab={changeSelectedTab}/>
                <div className="account-container">
                    {selectedTab === 'appointments' &&
                    <div>
                        <div className="tabs">
                            <button name="SCHEDULED" className={`tab ${status === 'SCHEDULED' ? 'selected' : '' }`} onClick={() => changeStatus('SCHEDULED')}>{t('scheduled')}</button>
                            <button name="COMPLETED" className={`tab ${status === 'COMPLETED' ? 'selected' : '' }`} onClick={() => changeStatus('COMPLETED')}>{t('completed')}</button>
                            <button name="REQUESTED" className={`tab ${status === 'REQUESTED' ? 'selected' : '' }`} onClick={() => changeStatus('REQUESTED')}>{t('requested')}</button>
                            <button name="CANCELLED" className={`tab ${status === 'CANCELLED' ? 'selected' : '' }`} onClick={() => changeStatus('CANCELLED')}>{t('cancelled')}</button>
                        </div>
                        <div className="appointments-container">
                            {appointments.map((appointment) => (
                                <div className="appointment-item">
                                    <Appointment appointment={appointment} accessToken={accessToken} />
                                </div>
                            ))}
                        </div>
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

                    {
                        selectedTab === 'clientinvoices' &&
                        <ClientInvoices/>
                    }

                </div>
            </div>

            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;