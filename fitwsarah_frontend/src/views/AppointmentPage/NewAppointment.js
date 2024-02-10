import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footer";
import BookingButton from "../../components/booking/bookingButton";
import './NewAppointment.css';
import { useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";

import AvailabilitiesCalendar from "./Calendar";

function BookAppointment() {
    const {isAuthenticated, getAccessTokenSilently } = useAuth0();
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const serviceId = queryParams.get("serviceId");
    const userId = queryParams.get("userId");
    const [setAccessToken] = useState(null);
    const [appointmentDataToSend, setAppointmentDataToSend] = useState(null);
    const { t } = useTranslation('appointment');
    
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

    const handleInputChange = (e) => {
        const { name, value } = e?.target || {};
        const updatedData = {
            ...appointmentDataToSend,
            availabilityId: "",
            userId: userId, 
            serviceId: serviceId,
            [name]: value,
        };
        console.log(updatedData)
        if (name === 'location' && value === '1') {
            window.alert("No location has been selected.")
            
        } else {
            setAppointmentDataToSend(updatedData);
        }
    };
    const handleDateInputChange = (selectedDate, selectedTime) => {
        const updatedData = {
            ...appointmentDataToSend,
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
      
    <AvailabilitiesCalendar onChange={(selectedDate, selectedTime) => handleDateInputChange(selectedDate, selectedTime)} />
      <form>
      <div className="form-group">
            <select id="address" name="location"  onChange={(e) => handleInputChange(e)}  required defaultValue="1">
                <option value="1" disabled>{t('selectLocation')}</option>
                <option value="Buzzfit, Brossard QC">Buzzfit, Brossard QC</option>
                <option value="Nautilus Plus, Brossard QC">Nautilus Plus, Brossard QC</option>
            </select>
        </div>
        <div className="form-group">
          <input type="text" id="firstName" maxLength="50" placeholder={t('firstName')} name="firstName" required  onChange={(e) => handleInputChange(e)} />
        </div>
        <div className="form-group">
          <input type="text" id="lastName" maxLength="50" placeholder={t('lastName')} name="lastName" required   onChange={(e) => handleInputChange(e)} />
        </div>
        <div className="form-group">
          <input type="tel" id="phone" maxLength="10" placeholder={t('phoneNumber')} name="phoneNum" required  onChange={(e) => handleInputChange(e)} />
        </div>
    </form>
    <div>
        <BookingButton id="bookBtn" appointmentDataToSend={appointmentDataToSend}/>
    </div>
    </div>

    </Container>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}
export default BookAppointment;