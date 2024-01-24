import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";

function AddServiceButton(fitnessDataToSend) {
    const {isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);

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

    const addNewService = async () => {
        try {
          await fetchData();
    
          if (!accessToken) {
              console.error("Access token not available.");
              return;
          }
    
          const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/fitnessPackages`, {
            method: "POST",
            headers: {
              Authorization: `Bearer ${accessToken}`,
              "Content-Type": "application/json",
            },
            body: JSON.stringify(fitnessDataToSend),
          }, [accessToken, fetchData])
          if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
              console.log(data);
              console.log("Added New Service");           
         } catch (error) {
          console.error("Error adding appointment: ", error);
          window.alert("An error has occured! Please try again later.");
      }};

      return (
    <div style={{ marginBottom: "15px" }}>
        <div style={{ width: "100%" }}>
        <form onSubmit={(e) => addNewService(e)}>
          <button style={{ width: "100%" }} id="serviceBtn" className="book-button" type="submit">
            Add
          </button>
          </form>
        </div>
      </div>
      );

}
export default AddServiceButton;