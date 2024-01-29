import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import '../PersonalTrainerPanel/TrainerFeedback.css';
import "../../css/style.css";
import { useTranslation } from "react-i18next";
function Feedbacks() {

    const {
        isAuthenticated,
    } = useAuth0();


    const [feedbacks, setFeedbacks] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');
    useEffect(() => {
        getAllFeedback();
    }, []);

    const getAllFeedback = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks`, {
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

    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">{t('back')}</Link>
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
                                    <td>{feedback.stars}</td>
                                    <td>{feedback.status}</td>
                                    <td>{feedback.content}</td>
                                    <td>
                                        <button className="button details-button">{t('publish')}</button>
                                        <button className="button delete-button">{t('remove')}</button>
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
