import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import configData from "../../config.json";
import { Modal } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useTranslation } from "react-i18next";

const AddFeedbackButton = ({feedbackDataToSend}) => {
    const {isAuthenticated, getAccessTokenSilently, loginWithRedirect, user } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [showSuggestion, setShowSuggestion] = useState(false);
    const { t } = useTranslation('contactMe');
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

    const addNewFeedback = async () => {
        try {
          await fetchData();

          if (!accessToken) {
              console.error("Access token not available.");
              return;
          }

      const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(feedbackDataToSend)
      })
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
        }
            const data = await response.json();
              console.log(feedbackDataToSend);
              console.log(data);
              console.log("Added New Feedback"); 
              window.alert("Feedback Successfully Added")        
         } catch (error) {
          console.error("Error adding service: ", error);
          window.alert("An error has occured! Please try again later.");
      }};

      function extractAfterPipe(originalString) {
        const parts = originalString.split('|');
        if (parts.length === 2) {
            return parts[1]; 
        } else {
            return originalString; 
        }
      };
      const { sub } = isAuthenticated ? user : {};
      const RegexUserId = sub ? extractAfterPipe(sub) : null;


      const getAccountInfo = async () => {
        try {
          await fetchData();

          if (!accessToken) {
              console.error("Access token not available.");
              return;
          }

        const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/users/${RegexUserId}`, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json",
        }})
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
        }
            const data = await response.json();
              console.log(data);
              const hasCompleteAppointment = data.some(appointment => appointment.status === "COMPLETED");

              if (!hasCompleteAppointment) {
                setShowSuggestion(e => !e);
                throw new Error(`No completed appointment found`);
                return false;
              } else {return true;}
         } catch (error) {
          console.error("Error adding feedback: ", error);
      }};

      const addNewFeedbackData = async (e) => {
        e.preventDefault();
        if (accessToken) {
          const accountInfo = await getAccountInfo();
          if (accountInfo){
          addNewFeedback();
          }
        }
      };

      return (
        <div style={{ marginBottom: "15px" }}>
        {!isAuthenticated && <button className="book-button" onClick={() => loginWithRedirect({authorizationParams: { screen_hint: "login"}})}>{t('submit')}</button>}
        {isAuthenticated &&
        <form onSubmit={(e) => addNewFeedbackData(e)}>
          <button id="newBtn" className="book-button" type="submit">{t('submit')}</button>
          </form>}

          <Modal show={showSuggestion} onHide={()=>setShowSuggestion(e=>!e)}>
        <Modal.Header closeButton>
          <Modal.Title>{t('tryFit')}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <h4>{t('seemsLike')}</h4>
         <h4>{t('youCanbook')}</h4>
         <Link to={`/`}><button className="book-button">{t('back')}</button></Link>
        </Modal.Body>

    </Modal>
        </div>


      );
}
export default AddFeedbackButton;