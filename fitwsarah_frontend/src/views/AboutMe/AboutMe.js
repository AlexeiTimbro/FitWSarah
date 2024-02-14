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
    const {t} = useTranslation('aboutMe');
    const {sub} = isAuthenticated ? user : {};


    function extractAfterPipe(originalString) {
        const parts = originalString.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return originalString;
        }
    }

    // Define a CustomImg component that applies a class name to the rendered img tag
    function CustomImg({ src, alt, width, height, className }) {
        // Include the `className` prop to allow custom class names
        return <img src={src} alt={alt} width={width} height={height} className={className} />;
    }






    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div id="contactBackground">
                <div className="container1">
                    <div className="box">
                        <p className="pargah1">{t('hello')}<br />
                            <span className="large-bold">{t('name')},</span><br />
                            {t('info')}</p>
                    </div>
                    <div className="box">
                        <div className="image-container">
                            <CustomImg src={workoutImage} alt="Workout" width="400" height="400" className="custom-img" />
                        </div>
                    </div>
                </div>

                <div className="container1">
                    <div className="box">
                        <div className="image-container1">
                            <CustomImg src={trainerImage} alt="Workout" width="200"  className="custom-img1" />
                    </div>
                    </div>
                    <div className="box">
                        <h2>{t('aboutus')}</h2>
                        <div className="p">
                        <p>{t('lineone')},<br></br>
                            {t('linetwo')}.{t('linethree')},<br></br>
                            {t('linefour')},<br></br>
                            {t('linefive')}<br></br>
                            {t('linesix')}.<br />
                            </p>
                        </div>
                    </div>
                </div>

                <div className="container1">
                    <div className="box">

                    </div>
                    <div className="box">
                        {/* for the feedback */}
                    </div>
                </div>

                <FooterNotLoggedIn />
            </div>
        </div>
    );
}



export default AboutMe;

