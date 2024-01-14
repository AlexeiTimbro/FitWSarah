import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import '/app/src/components/navigation/NotLoggedIn/navNotLoggedIn.css'; 
/*
const [isAuthenticated,getAccessTokenSilently,loginWithRedirect] = useAuth0();


const BookingButton = () => {
    const addNewAppointment = () => {
        fetch("http://localhost:8080/api/v1/appointments", {
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
  return <button className="book-button"  onClick={() => addNewAppointment()}>
  Book
</button>
};

export default BookingButton;
*/
