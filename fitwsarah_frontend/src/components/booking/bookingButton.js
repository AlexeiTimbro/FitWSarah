import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";

const BookingButton = ({appointmentDataToSend}) => {
  const { isAuthenticated, getAccessTokenSilently } = useAuth0();
  const [accessToken, setAccessToken] = useState(null);

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

      const response = await fetch(`http://localhost:8080/api/v1/appointments`, {
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
       
     } catch (error) {
      console.error("Error adding appointment: ", error);
      window.alert("An error has occured! Please try again later.");
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
          Confirm
        </button>
        </form>
      </div>
    </div>
  );
};

export default BookingButton;