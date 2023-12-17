import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import ProfileSideBar from "../../components/clientProfile/profile";
import {Link} from "react-router-dom";
import Sidebar from '../../components/clientProfile/sidebar';
function Profile() {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();

    const [accessToken, setAccessToken] = useState(null);
    const [profile, setProfile] = useState(null);

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
        }
    }, [accessToken]);

    const getAccountById = () => {
        fetch("http://localhost:8080/api/v1/accounts/uuid-acc2", {
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
            <section className="hero-section1"></section>
            <section>
                <ProfileSideBar/>
            </section>


            <section className="services-section">
                <Container>
                    {/* Center the welcome text */}
                    <Row className="justify-content-center">
                        <Col md={8} className="text-center">
                            <h1 style={{color: "white"}}>
                                Welcome {profile && profile.username}
                            </h1>
                        </Col>
                    </Row>
                </Container>
            </section>


            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;