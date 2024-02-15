import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footer";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import "../../components/authentication/switch.css"
import 'react-toastify/dist/ReactToastify.css';
import {useGetAccessToken} from "../../components/authentication/authUtils";
import "./AboutMe.css";
import { useTranslation } from "react-i18next";
import workoutImage from './workout.png';
import trainerImage from './trainer.png';
import FeedbackCard from "../../components/AboutMe/feedbackCard";
import { Swiper, SwiperSlide } from "swiper/react";
import 'swiper/css';

function AboutMe() {
    const {
        isAuthenticated
    } = useAuth0();
    const [feedbacks, setFeedbacks] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [searchTerm] = useState([["status","VISIBLE"]]);
    const { t } = useTranslation('aboutMe');

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
    }, [accessToken, searchTerm, isAuthenticated]);

    const getAllFeedback = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
            if (term[1]) {
                params.append(term[0], term[1]);
            }
        });

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/feedbacks${params.toString() && "?" + params.toString()}`, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + accessToken,
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
            console.log(data);
            setFeedbacks(data);
        })
        .catch((error) => {
            console.log(error);
        });
    };
    // Define a CustomImg component that applies a class name to the rendered img tag
    function CustomImg({ src, alt, width, height, className }) {
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
                            <p>{t('lineone')}
                                {t('linetwo')}
                                {t('linethree')}
                                {t('linefour')}
                                {t('linefive')}
                            </p>
                        </div>
                    </div>
                </div>
                {isAuthenticated && (
                <div className="container1">  
                    <div className="box">
                    <h2 className="recentTitle">{t('feedbacks')}<br /></h2>
                    <Swiper slidesPerView={2}spaceBetween={15}className="mySwiper">
                        <div class="scrolling-wrapper-flexbox">
                            {feedbacks.map(feedback => (
                                <SwiperSlide>
                                <div class="card" key={feedback.id}>
                                <FeedbackCard feedback={feedback} />
                                </div>
                                </SwiperSlide>
                            ))}
                        </div>
                    </Swiper>
                    </div>
                    
                </div>
                )}
                <FooterNotLoggedIn />
            </div>
        </div>
    );
}



export default AboutMe;

