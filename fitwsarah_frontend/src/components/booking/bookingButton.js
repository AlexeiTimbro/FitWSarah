import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";

const BookingButton = ({appointmentData, setAppointmentData}) => {
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

  const addNewAppointment = () => {
    fetch(`http://localhost:8080/api/v1/appointments?serviceId=${appointmentData.serviceId}`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${accessToken}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(appointmentData),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok brotha " + response.statusText);
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
        window.alert("Appointment booked successfully!");
      })
      .catch((error) => {
        console.error("Error fetching details:",error);
        window.alert("An error has occured! Please try again later.");
      });
      
  };

  return (
    <div style={{ marginBottom: "15px" }}>
      <div style={{ width: "100%" }}>
        <button style={{ width: "100%" }} id="newBtn" className="book-button" type="submit" onClick={addNewAppointment}>
          Confirm
        </button>
      </div>
    </div>
  );
};

export default BookingButton;
