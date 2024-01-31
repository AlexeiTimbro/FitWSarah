import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { useTranslation } from "react-i18next";

const AddServiceButton = ({getAllFitnessServices ,fitnessDataToSend, setShowForm}) => {
    const {isAuthenticated, getAccessTokenSilently } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('home');

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
      })
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
        }
            const data = await response.json();
              console.log(data);
              console.log("Added New Service"); 
              window.alert("Service Successfully Added")  
              getAllFitnessServices();
              setShowForm(false);      
         } catch (error) {
          console.error("Error adding service: ", error);
          window.alert("An error has occured! Please try again later.");
      }};
      const addNewServiceData = (e) => {
        e.preventDefault();
        const result = window.confirm("Are you sure you want to proceed?");
        if (result && accessToken){
          addNewService();
        }
        else {
          getAllFitnessServices();
          setShowForm(false);
        }
      };
  
  return (
    <div style={{ marginBottom: "15px" }}>
      <div style={{ width: "100%" }}>
      <form onSubmit={(e) => addNewServiceData(e)}>
        <button style={{ width: "100%" }} id="newBtn" className="book-button" type="submit">
          {t('addService')}
        </button>
        </form>
      </div>
    </div>
  );
};
export default AddServiceButton;