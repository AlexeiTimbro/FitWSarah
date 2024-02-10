import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./Appointment.css";
import { useTranslation } from "react-i18next";
import { useLanguage } from "../../LanguageConfig/LanguageContext";

function Appointment( {appointment, accessToken} ) {

    const [service, setService] = useState(null);
    const [show, setShow] = useState(false);
    const [requestedCancel, setRequestedCancel] = useState(false);
    const [scheduledCancel, setScheduledCancel] = useState(false);
    const [reschedule, setReschedule] = useState(false)
    const { t } = useTranslation('appointment');
    const { language } = useLanguage();
    const date = new Date();

    const formattedDate = 
        date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0') + 'T' +
        String(date.getHours()).padStart(2, '0') + ':' +
        String(date.getMinutes()).padStart(2, '0') + ':' +
        String(date.getSeconds()).padStart(2, '0');

    const [currentDateTime, setCurrentDateTime] = useState(formattedDate);

    useEffect(() => {
        getService(appointment.serviceId);
        {console.log(currentDateTime)}
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

    function cancelAppointment(appointmentId) {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointmentId}/cancelled`, {
            method: "PUT",
            headers: {
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            },
            body: JSON.stringify("CANCELLED")
        })
        .then((response) => {
            if (!response.ok) {
                console.error("Response status: " + response.status);
                throw new Error('Network response was not ok ' + response.statusText);
            }
            console.log("Appointment cancelled");
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
                {appointment.status === "REQUESTED" && 
                <button className="cancel-appointment-button" onClick={() => setRequestedCancel(!requestedCancel)}>{t('appointmentCancel')}</button>}
                {appointment.status === "SCHEDULED" && 
                <button className="cancel-appointment-button" onClick={() => setScheduledCancel(!scheduledCancel)}>{t('appointmentCancel')}</button>}
                {appointment.status === "SCHEDULED" && 
                <button className="reschedule-appointment-button" onClick={() => setReschedule(!reschedule)}>{t('appointmentReschedule')}</button>}
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

        <Modal show={requestedCancel} onHide={() => setRequestedCancel(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{t('cancelAppointment')}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{t('appointmentCancelMessage')}</Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button className="cancel-appointment-button" onClick={() => {
                    setRequestedCancel(false);
                    cancelAppointment(appointment.appointmentId);
                    }}>{t('appointmentConfirm')}</button>
                <button className="view-details-button" onClick={() => setRequestedCancel(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>
        </Modal>

        <Modal show={scheduledCancel} onHide={() => setScheduledCancel(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{t('cancelAppointment')}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{t('appointmentCancelMessage')}</Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button className="cancel-appointment-button" onClick={() => {
                    {   const appointmentDate = new Date(appointment.date);
                        const currentDate = new Date(currentDateTime);

                        const difference = currentDate - appointmentDate;
                        const differenceInDays = difference / (1000 * 60 * 60 * 24);

                        if (differenceInDays > 2) {
                            cancelAppointment(appointment.appointmentId);
                            setScheduledCancel(false);
                          } else {
                            alert(t('invalidCancel'));
                            setScheduledCancel(false);
                        }
                    }
                    }}>{t('appointmentConfirm')}</button>
                <button className="view-details-button" onClick={() => setScheduledCancel(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>
        </Modal>
    </div>
  );
}

export default Appointment;