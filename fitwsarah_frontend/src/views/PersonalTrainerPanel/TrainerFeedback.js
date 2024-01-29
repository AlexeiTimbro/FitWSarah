import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerAccounts.css';
import "../../css/style.css";
import ReactStars from 'react-stars';
import './TrainerFeedback.css';
import { useTranslation } from "react-i18next";
function Feedbacks() {

    const {
        isAuthenticated,
    } = useAuth0();


    const [feedbacks, setFeedbacks] = useState([]);
    const { t } = useTranslation('adminPanel');
    useEffect(() => {
        getAllFeedback();
    }, []);

    const getAllFeedback = () => {
        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/feedbacks`, {
            method: "GET",
            headers: new Headers({
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
                setFeedbacks(data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const removeFeedback = (feedbackId) => {
        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/feedbacks/${feedbackId}`, {
            method: "DELETE",
            headers: new Headers({
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
            setFeedbacks(data);
        })
        .catch((error) => {
            console.log(error);
        });
    };

    const removeConfirmation  = (feedbackId) => {
        const answer = window.confirm("Are you sure?");
        if(answer){
            removeFeedback(feedbackId);
        }
        else{
            return;
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
                                        <button className="button details-button">{t('publish')}</button>
                                        <button className="button delete-button" onClick={removeConfirmation(feedback.id)}>{t('remove')}</button>
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
