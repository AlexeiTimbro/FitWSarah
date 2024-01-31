import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import configData from "../../config.json";
import './TrainerAccounts.css';
import "../../css/style.css";
import ReactStars from 'react-stars';
import './TrainerFeedback.css';
import { useTranslation } from "react-i18next";
import {useGetAccessToken} from "../../components/authentication/authUtils";
function Feedbacks() {

    const {
        isAuthenticated,
    } = useAuth0();


    const [feedbacks, setFeedbacks] = useState([]);
    const { t } = useTranslation('adminPanel');
    const [accessToken, setAccessToken] = useState(null);
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
            getAllFeedback();
        }
    }, [accessToken]);

        const getAllFeedback = async () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks`, {
            method: "GET",
            headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            }
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                setFeedbacks(data);
            })
            .catch((error) => {
                console.log(error);
            });
        };

    const removeFeedback = async (feedbackId) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks/${feedbackId}`, {
            method: "DELETE",
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "application/json"
        }})
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
        })
        .then(() =>{
            getAllFeedback();
        })
       
    };

    const updateFeedback = async (feedbackId, status) => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks/${feedbackId}/publish`, {
            method: "PATCH",
            headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            },
            body: status
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            console.log(data)
            getAllFeedback();
        })
        .catch((error) => {
            console.log(error);
        });
    } 

    const removeConfirmation  = (feedbackId) => {
        const answer = window.confirm(t("areyousure"));
        if(answer){
            removeFeedback(feedbackId);
            getAllFeedback();
        }
    }

    const publishConfirmation  = (feedbackId) => {
        const answer = window.confirm(t("areyousure_publish"));
        if(answer){
            updateFeedback(feedbackId, 'VISIBLE');
        }
    }

    const unpublishConfirmation  = (feedbackId) => {
        const answer = window.confirm(t('areyousure_UNpublish'));
        if(answer){
            updateFeedback(feedbackId, 'INVISIBLE');
        }
    }

    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/trainerPanel" className="button back-button">{t('back')}</Link>
                    <div className="header-section">
                        <h1>{t('feedback')}</h1>
                    </div>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('userId')}</th>
                                <th>{t('stars')}</th>
                                <th>{t('status')}</th>
                                <th>{t('content')}</th>
                                <th>{t('actions')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {feedbacks.map(feedback => (
                                <tr key={feedback.id}>
                                    <td>{feedback.userId}</td>
                                    <td><ReactStars count={feedback.stars} size={20} half={false}  color1={'#ffd700'} /></td>
                                    <td>{feedback.status}</td>
                                    <td>{feedback.content}</td>
                                    <td>
                                    
                                    {feedback.status === "INVISIBLE" && (
                                         <button className="button details-button" onClick={() => publishConfirmation(feedback.feedbackId)}>{t('publish')}</button>
                                            )}

                                        {feedback.status === "VISIBLE" && (
                                         <button className="button details-button" onClick={() => unpublishConfirmation(feedback.feedbackId)}>{t('unpublish')}</button>
                                            )}

                                        <button className="button delete-button" onClick={() => removeConfirmation(feedback.feedbackId)}>{t('remove')}</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            
        </div>
    );
}

export default Feedbacks;
