import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import { useParams } from "react-router-dom";
import {Link} from "react-router-dom";
import AddMemberProfile from "../authentication/Signup";

function BookAppointment() {
    const { isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [appointmentData, setAppointmentData] = useState({
        firstName: "",
        lastName: "",
        location: "",
        date: "",
        phoneNum: ""
    });
//Maybe have phone num?
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

    const addNewAppointment = () => {
        fetch("http://localhost:8080/api/v1/appointments", {
            method: "POST",
            headers: {
                 Authorization: `Bearer ${accessToken}`,
               "Content-Type": "application/json"
            },
             body: JSON.stringify(appointmentData)
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
            setAppointmentData(data);
        })
        .catch((error) => {
            console.error(
                "Error fetching service details for serviceId",
                ":",
                error
            );
        });
    };
    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setAppointmentData({ ...appointmentData, [name]: value });
    };

    useEffect(() => {
        if (accessToken) {
            addNewAppointment();
        }
    }, [accessToken]);

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
        <input style={{ width: "40%" }} name="firstName" maxLength="50" pattern="^[\x20-\x7F]+$" 
        required value={appointmentData.firstName} onChange={handleInputChange}/>
            <span ng-show="appointmentForm.firstName.$error.required">First name is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Last Name</label>
        <div style="width: 60%;" class="appointment">
        <input style={{ width: "40%" }} name="lastName" maxLength="50" pattern="^[\x20-\x7F]+$" 
        required value={appointmentData.lastName} onChange={handleInputChange}/>
            <span ng-show="appointmentForm.lastName.$error.required">Last name is required.</span>
        </div>
    </div>
   
    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Location</label>
        <div style="width: 60%;" class="appointment">
        <input style={{ width: "40%" }} name="location" maxLength="50" pattern="^[\x20-\x7F]+$" 
        required value={appointmentData.location} onChange={handleInputChange}/>
            <span ng-show="appointmentForm.address.$error.required">Address is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Available Dates</label>
        <div style="width: 60%;" class="appointment">
        <input style={{ width: "40%" }} name="date" maxLength="50" pattern="^[\x20-\x7F]+$" 
        required value={appointmentData.date} onChange={handleInputChange}/>
            <span ng-show="appointmentForm.address.$error.required">Date is required.</span>
            <span>Date could be subject to change.</span>
        </div>
    </div>    

    <div style="margin-bottom: 15px;">
        <label style="width: 10%;" class="control-label">Phone Number</label>
        <div style="width: 60%;" class="appointment">
        <input style={{ width: "40%" }} name="phoneNum" maxLength="50" pattern="^[\x20-\x7F]+$" 
        required value={appointmentData.phoneNum} onChange={handleInputChange}/>
            <span ng-show="appointmentForm.telephone.$error.required">Phone Number is required.</span>
        </div>
    </div>

    <div style="margin-bottom: 15px;">
        <div style="width: 60%;" class="appointment">
            <button style="width: 100%;" id="newBtn" class="btn btn-default" type="submit" onClick={addNewAppointment()}>Reserve</button>
        </div>
    </div>
</form>
    </Container>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}

export default BookAppointment;