import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import "./coachNote.css"


function CoachNote() {
    
    const { user } = useAuth0();
    const [contents, setContents] = useState([]);
    const [access, setAccess] = useState(false);

    useEffect(() => {
        const fetchToken = async () => {
          const token = await getAccessToken();
          if (token) setAccessToken(token);
        };
  
        fetchToken();
      }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getCoachNote(extractAfterPipe(user.sub));
        }
    }, [accessToken]);

    function extractAfterPipe(userId) {
        const parts = userId.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return userId;
        }
    }

    function getCoachNote(userId) {
        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/coachnotes/uses/${userId}`, {
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
            setContents(data.content);
        })
        .catch((error) => {
            console.log(error);
        });
    };


    return (
        <div className="scroll-container">
            <div className="box">
                {contents.map((content, index) => (
                    <div className="coachnote" key={index}>
                        <div className="overlap-group">
                            <div className="group">
                                <div className="card-title1">Coach Note {index + 1}</div>
                                <div className="card-detail1">{content}</div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default CoachNote;