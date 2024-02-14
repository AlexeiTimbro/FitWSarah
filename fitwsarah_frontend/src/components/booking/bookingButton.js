import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom"

const BookingButton = ({appointmentDataToSend}) => {
  const { isAuthenticated, getAccessTokenSilently } = useAuth0();
  const [accessToken, setAccessToken] = useState(null);
  const { t } = useTranslation('appointment');
  const navigate = useNavigate()
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
  
  const fetchData = async () => {
    try {
        const token = await getAccessTokenSilently({
            audience: configData.audience,
            scope: configData.scope,
        });
        setAccessToken(token);
    } catch (error) {
        console.error("Error getting access token: ", error);
    }
  };

  const addNewAppointment = async () => {
    try {
      await fetchData();

      if (!accessToken) {
          console.error("Access token not available.");
          return;
      }
      const fields = ['date', 'time', 'location', 'firstName', 'lastName', 'phoneNum'];
      for (const field of fields) {
      if (!appointmentDataToSend[field]) {
         alert(t('completeFields'));
          return;
        }
      }
    const phoneRegex = /^[0-9]{3}-[0-9]{3}-[0-9]{4}$/;
        if (!phoneRegex.test(appointmentDataToSend.phoneNum)) {
            alert(t('phoneValid'));
            return;
        }
      const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(appointmentDataToSend),
      }, [accessToken, fetchData])
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const data = await response.json();
          console.log(data);
          console.log("Added Appointment");
          window.alert("Appointment Successfully booked")
          navigate(`/`)
     } catch (error) {
      console.error("Error adding appointment: ", error);
  }}

  const addNewAppointmentData = (e) => {
    e.preventDefault();
    if (accessToken) {
      addNewAppointment();
  }
  };
  
  return (
    <div style={{ marginBottom: "15px" }}>
      <div style={{ width: "100%" }}>
      <form onSubmit={(e) => addNewAppointmentData(e)}>
        <button style={{ width: "100%" }} id="newBtn" className="book-button" type="submit">
          {t('confirmBooking')}
        </button>
        </form>
      </div>
    </div>
  );
};

export default BookingButton;