import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import { useParams } from "react-router-dom";

import {Link} from "react-router-dom";
import "./Account.css"

function Profile() {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [appointment, setAppointment] = useState([]);

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
    const { accountId } = useParams();
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

    const addNewAppointment = () => {
        fetch("http://localhost:8080/api/v1/accounts", {
            method: "POST",
            headers: {
                 Authorization: `Bearer ${accessToken}`,
               "Content-Type": "application/json"
            },
             body: JSON.stringify(dataToSend)
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
            setAppointment(data);
        })
        .catch((error) => {
            console.error(
                "Error fetching service details for serviceId",
                ":",
                error
            );
        });
    };
//Location should be Dropbar, or map, date should be the calendar
    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <section className="hero-section1"></section>
            <section className="services-section">
                <Container>
        
<form id="appointmentForm" style="margin-top: 20px;" enctype="multipart/form-data">
    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">First Name</label>
        <div style="width: 60%;" class="appointment">
            <input style="width: 40%;" name="firstName"
                    maxlength="50" pattern="^[\x20-\x7F]+$" required />
            <span ng-show="appointmentForm.firstName.$error.required">First name is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Last Name</label>
        <div style="width: 60%;" class="appointment">
            <input style="width: 40%;" name="lastName"
                  maxlength="50" pattern="^[\x20-\x7F]+$" required />
            <span ng-show="appointmentForm.lastName.$error.required">Last name is required.</span>
        </div>
    </div>
        
    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Location</label>
        <div style="width: 60%;" class="appointment">
            <input style="width: 40%;" name="location"
                maxlength="50" pattern="^[\x20-\x7F]+$" required />
            <span ng-show="appointmentForm.address.$error.required">Address is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Suggested Date</label>
        <div style="width: 60%;" class="appointment">
            <input style="width: 40%;" name="date"
                maxlength="50" pattern="^[\x20-\x7F]+$" required />
            <span ng-show="appointmentForm.address.$error.required">Date is required.</span>
            <span>Date could be subject to change.</span>
        </div>
    </div>    

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Phone Number</label>
        <div style="width: 60%;" class="appointment">
            <input style="width: 40%;"  name="phoneNum"
                   maxlength="50" pattern="^[\x20-\x7F]+$" required />
            <span ng-show="appointmentForm.telephone.$error.required">Phone Number is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <div style="width: 60%;" class="appointment">
            <button style="width: 100%;" id="newBtn" class="btn btn-default" type="submit">Submit</button>
        </div>
    </div>
</form>
    </Container>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}

export default Profile;