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

function Profile() {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();
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
            getAppointmentsByAccountId("dc2b4f0f-76da-4d1e-ad2d-cebf950e5fa2");
        }
    }, [accessToken]);

    // Extract accountId from URL params
    const { accountId } = useParams();

    const getAccountById = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/accounts/${id}`, {
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
            console.error("Error fetching account details:", error);
        }
    };

    const getAppointmentsByAccountId = async (id) => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/appointments/account/${id}`, {
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
            setAppointments(data);
        } catch (error) {
            console.error("Error fetching appointments:", error);
        }
    };

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div className="profile-page-container">
                <div className="profile-header">
                    <div className="cover-image"></div>
                    <button className="edit-profile-btn">Edit Profile</button>
                </div>
               </div>
                <section className="services-section">
                    <Container>
                        <h1 style={{ color: "white" }}>
                            Welcome {profile && profile.username}
                        </h1>
                        <Row>
                            {profile && (
                                <Col md={8}>
                                    <div id="serviceCard" className="service-card">
                                        <h2> Personal Info </h2>
                                        <p> Email Address: {profile.email}</p>
                                        <p> City of Residence: {profile.city}</p>
                                    </div>
                                </Col>
                            )}
                        </Row>
                    </Container>
                </section>
                <div className="account-container">
                    <div className="tabs">
                        <button className="tab">Today</button>
                        <button className="tab">Scheduled</button>
                        <button className="tab">Finished</button>
                    </div>
                    <ProfileSideBar appointments={appointments} accessToken={accessToken} />
                </div>

            <FooterNotLoggedIn />
        </div>
    );
}

export default Profile;
