import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./Appointment.css";
import { useTranslation } from "react-i18next";
import { useLanguage } from "../../LanguageConfig/LanguageContext";

function Appointment( {appointment, accessToken} ) {

    const [service, setService] = useState(null);
    const [show, setShow] = useState(false);
    const { t } = useTranslation('appointment');
    const { language } = useLanguage();

    useEffect(() => {
        getService(appointment.serviceId);
    }, []);

    function getService(serviceId) {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/fitnessPackages/${serviceId}`, {
            method: "GET",
            headers: {
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            }
        })
        .then((response) => {
            if (!response.ok) {
                console.error("Response status: " + response.status);
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then((data) => {
            console.log(data);
            setService(data);
        })
        .catch((error) => {
            console.error("Error message:", error.message);
        });
    };


  return (
    <div>
        <div className="card-container">
            <div className="card-content">
                <div>
                    <div className="card-details">
                        <div className="card-title">{language === 'en' ? service?.title_EN : service?.title_FR}</div>
                        <div className="card-detail">{t('appointmentLocation')}{appointment.location}</div>
                        <div className="card-detail">{t('appointmentDate')}{appointment.date}</div>
                        <div className="card-detail">{t('appointmentTime')}{appointment.time}</div>
                    </div>
                </div>
                <button className="view-details-button" onClick={() => setShow(!show)}>{t('appointmentViewDetails')}</button>
            </div>
        </div>
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{service?.title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{t('appointmentDescription')}{language === 'en' ? service?.description_EN : service?.description_FR}</Modal.Body>
            <Modal.Body>{t('appointmentOtherInformation')}{language === 'en' ? service?.otherInformation_EN : service?.otherInformation_FR}</Modal.Body>
            <Modal.Body>{t('appointmentPrice')}{service?.price}$</Modal.Body>
            <Modal.Body>{t('appointmentDuration')}{service?.duration}</Modal.Body>
            <Modal.Body>{t('appointmentLocation')}{appointment.location}</Modal.Body>
            <Modal.Body>{t('appointmentDate')}{appointment.date}</Modal.Body>
            <Modal.Body>{t('appointmentTime')}{appointment.time}</Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button className="view-details-button" onClick={() => setShow(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>
        </Modal>
    </div>
  );
}

export default Appointment;