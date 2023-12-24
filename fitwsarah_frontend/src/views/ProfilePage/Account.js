import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import { useParams } from "react-router-dom";
import { Link } from "react-router-dom";
import "../../css/style.css";
import "../../css/Account.css";
function Profile() {
    const {isAuthenticated, getAccessTokenSilently} = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);
    const [appointments, setAppointments] = useState([]);

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
            getAccountById();
            getAppointmentsByAccountId();
        }
    }, [accessToken]);

    const {accountId} = useParams();

    const getAccountById = () => {
        fetch(`http://localhost:8080/api/v1/accounts/${accountId}`, {
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
                console.log(data);
                setProfile(data);
            })
            .catch((error) => {
                console.error(
                    "Error fetching service details for accountId",
                    accountId,
                    ":",
                    error
                );
            });
    };

    const getAppointmentsByAccountId = () => {
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
                console.log(data);
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
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div className="box">
                <div className="profile-header">
                    <div className="overlap">
                        <div className="overlap-group">
                            <img className="rectangle" alt="Rectangle" src="Rectangle-9840.jpg" />
                            <img className="ellipse" alt="Ellipse" src="Ellipse-1631.png" />
                            <div className="group">
                                <div className="div">
                                    <div className="rectangle-2" />
                                    <div className="text-wrapper">Edit Cover</div>
                                </div>
                            </div>
                        </div>
                        <div className="overlap-group-wrapper">
                            <div className="div-wrapper">
                                <div className="text-wrapper-2">Edit Profile</div>
                            </div>
                        </div>
                        <div className="group-2">
                            <div className="text-wrapper-3">Profile Name {profile && profile.username}</div>
                            <div className="text-wrapper-4">Welcome Back!</div>
                        </div>
                    </div>
                </div>
            </div>
            <section>
                <div className="account-container">
                    <div className="tabs">
                        <button className="tab">Today</button>
                        <button className="tab">Scheduled</button>
                        <button className="tab">Finished</button>
                    </div>
                    <ProfileSideBar appointments={appointments} accessToken={accessToken} />
                </div>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;