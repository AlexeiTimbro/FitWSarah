import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import "./CoachNote.css"
import { useGetAccessToken } from "../authentication/authUtils";
import { useLanguage } from "../../LanguageContext/LanguageContext.js";
import { useTranslation } from "react-i18next";


function CoachNote({userId}) {
    
    const [contents, setContents] = useState([]);
    const [accessToken, setAccessToken] = useState(false);
    const { language } = useLanguage();
    const { t } = useTranslation();
    const getAccessToken = useGetAccessToken();

    useEffect(() => {
        const fetchToken = async () => {
          const token = await getAccessToken();
          if (token) setAccessToken(token);
        };
  
        fetchToken();
      }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getCoachNote(userId);
        }
    }, [accessToken]);

    function getCoachNote(userId) {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/coachnotes/users/${userId}`, {
            method: "GET",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            })
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            console.log(data);
            setContents(data);
        })
        .catch((error) => {
            console.log(error);
        });
    };


    return (
        <div className="notes-container">
            <h1>{t('coachNoteTitle')}</h1>
            <div className="note-row">
                {contents && contents.map((content, index) => (
                    <div className="note-card" key={index}>
                    <div className="note-title">{t('coachNote')} {index + 1}</div>
                    <div className="note-content">{content.content}</div>
                </div>
                ))}
            </div>
        </div>
    )
}

export default CoachNote;