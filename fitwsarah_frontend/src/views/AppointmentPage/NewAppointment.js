import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import BookingButton from "../../components/booking/bookingButton";
import './NewAppointment.css';
import { useLocation } from "react-router-dom";

import AvailabilitiesCalendar from "./Calendar";

function BookAppointment() {
    const {isAuthenticated, getAccessTokenSilently } = useAuth0();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const serviceId = queryParams.get("serviceId");
    const userId = queryParams.get("userId");
    const [accessToken, setAccessToken] = useState(null);
    const [appointmentDataToSend, setAppointmentDataToSend] = useState(null);
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

    const handleInputChange = (e, selectedDate = null, selectedTime = null) => {
        const { name, value } = e?.target || {};
        const updatedData = {
            ...appointmentDataToSend,
            availabilityId: "",
            userId: userId, 
            serviceId: serviceId,
            [name]: value,
            date: selectedDate, 
            time: selectedTime
        };
        console.log(updatedData)
        setAppointmentDataToSend(updatedData)
    };
    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <section className="appointmentSection">
                <Container>
                <div className="reservation-form">
      
    <AvailabilitiesCalendar onChange={(selectedDate, selectedTime, e) => handleInputChange(e, selectedDate, selectedTime)} />
      <form>
      <div className="form-group">
            <select id="address" name="location"  onChange={(e) => handleInputChange(e)}  required>
                <option value="1" disabled >Select an available location</option>
                <option value="Buzzfit, Brossard QC">Buzzfit, Brossard QC</option>
                <option value="Nautilus Plus, Brossard QC">Nautilus Plus, Brossard QC</option>
            </select>
        </div>
        <div className="form-group">
          <input type="text" id="firstName" placeholder="First Name" name="firstName"  onChange={(e) => handleInputChange(e)}  required />
        </div>
        <div className="form-group">
          <input type="text" id="lastName" placeholder="Last Name" name="lastName"   onChange={(e) => handleInputChange(e)}  required />
        </div>
        <div className="form-group">
          <label htmlFor="phone"></label>
          <input type="tel" id="phone" placeholder="Phone Number" name="phoneNum"   onChange={(e) => handleInputChange(e)}  required />
        </div>
    </form>
    <div>
        <BookingButton appointmentDataToSend={appointmentDataToSend}/>
    </div>
    </div>

    </Container>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}
export default BookAppointment;