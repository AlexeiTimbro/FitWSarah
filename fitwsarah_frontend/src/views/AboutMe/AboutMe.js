import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footer";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { ROLES } from "../../components/authentication/roles";
import "../../components/authentication/switch.css"
import RoleBasedSwitch from "../../components/authentication/RoleBasedSwitch";
import 'react-toastify/dist/ReactToastify.css';
import ReactStars from 'react-stars'
import AddFeedbackButton from '../../components/feedback/newFeedbackBtn';
import "./AboutMe.css";
import { useTranslation } from "react-i18next";
import workoutImage from './workout.png';
import trainerImage from './trainer.png';
function AboutMe() {
    const {
        isAuthenticated,
        user
    } = useAuth0();

    const [editMode, setEditMode] = useState(false);
    const {t} = useTranslation('contactMe');

    function extractAfterPipe(originalString) {
        const parts = originalString.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return originalString;
        }
    }

    const {sub} = isAuthenticated ? user : {};
    const RegexUserId = sub ? extractAfterPipe(sub) : null;

    const [feedbackDataToSend, setFeedbackDataToSend] = useState({});

    const handleInputChange = (e) => {
        const {name, value} = e?.target || {};
        const updatedData = {
            ...feedbackDataToSend,
            userId: RegexUserId,
            [name]: value,
        };
        console.log(updatedData)
        setFeedbackDataToSend(updatedData);
    };

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div id="contactBackground">
                <div className="container">
                    <div className="box">
                        <p>Hello, I am<br />
                            <span className="large-bold">Sarah Siddiqui,</span><br />
                            A professional personal fitness trainer.</p>
                    </div>
                    <div className="box">
                        <div className="image-container">
                        <img src={workoutImage} alt="Workout" width={400} height={400}/>
                        </div>
                    </div>
                </div>

                <div className="container">
                    <div className="box">
                        <div className="image-container">
                        <img src={trainerImage} alt="Trainer" />
                    </div>
                    </div>
                    <div className="box">
                        <h2>About Us</h2>
                        <p>I am a dedicated personal trainer with many years of expertise.<br />
                            I am fully trained and qualified and I would be happy to have you as a client.</p>
                    </div>

                </div>

                <div className="container">
                    <div className="box">

                    </div>
                    <div className="box">
                        {/* Content for the second half */}
                    </div>
                </div>

                <FooterNotLoggedIn />
            </div>
        </div>
    );
}



export default AboutMe;

