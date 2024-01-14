import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Container, Row, Col } from "react-bootstrap";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import BookingButton from "../../components/booking/bookingButton";
import './NewAppointment.css';
import { useParams } from "react-router-dom";
import AvailabilitiesCalendar from "./Calendar";

function BookAppointment() {
    const {isAuthenticated, getAccessTokenSilently } = useAuth0();
    const { serviceId, accountId } = useParams();
    const [accessToken, setAccessToken] = useState(null);
    const [appointmentData, setAppointmentData] = useState({
        availabilityId:"",
        accountId: accountId,
        serviceId: serviceId,
        firstName: "",
        lastName: "",
        location: "",
        date: "",
        time: "",
        phoneNum: ""
    });

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

   
    const handleInputChange = (e, selectedDate, selectedTime) => {
        if (e) {
            const { name, value } = e.target;
            setAppointmentData({
              ...appointmentData,
              [name]: value,
              date: selectedDate,
              time: selectedTime,
            });
          } else {
            setAppointmentData({ ...appointmentData,
                date: selectedDate, 
                time: selectedTime
            });
          }
    };
    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <section className="appointmentSection">
                <Container>
                <div className="reservation-form">
      
    <AvailabilitiesCalendar onChange={(selectedDate, selectedTime) => handleInputChange(null, selectedDate, selectedTime)} />
      <form>
      <div className="form-group">
            <select id="address" name="location" value={appointmentData.location} onChange={handleInputChange} required>
                <option value="" disabled>Select an available location</option>
                <option value="Buzzfit, Brossard QC">Buzzfit, Brossard QC</option>
                <option value="Nautilus Plus, Brossard QC">Nautilus Plus, Brossard QC</option>
            </select>
        </div>
        <div className="form-group">
          <input type="text" id="firstName" placeholder="First Name" name="firstName" value={appointmentData.firstName} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <input type="text" id="lastName" placeholder="Last Name" name="lastName"  value={appointmentData.lastName} onChange={handleInputChange} required />
        </div>
        <div className="form-group">
          <label htmlFor="phone"></label>
          <input type="tel" id="phone" placeholder="Phone Number" name="phoneNum"  value={appointmentData.phoneNum} onChange={handleInputChange} required />
        </div>
        <div>
        <BookingButton appointmentData={appointmentData} setAppointmentData={setAppointmentData}/>
    </div>
    </form>
    </div>

    </Container>
            </section>
            <FooterNotLoggedIn/>
        </div>
    );
}
export default BookAppointment;