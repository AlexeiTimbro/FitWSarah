import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./Appointment.css";
import { useTranslation } from "react-i18next";
import { useLanguage } from "../../LanguageConfig/LanguageContext";
import { format } from "date-fns";
import AvailabilitiesCalendar from "../../views/AppointmentPage/Calendar";

function Appointment( {appointment, accessToken, reloadAppointment} ) {

    const [service, setService] = useState(null);
    const [show, setShow] = useState(false);
    const [requestedCancel, setRequestedCancel] = useState(false);
    const [scheduledCancel, setScheduledCancel] = useState(false);
    const [reschedule, setReschedule] = useState(false)
    const { t } = useTranslation('appointment');
    const { language } = useLanguage();
    const date = new Date();
    const [updatedAppointment, setUpdatedAppointment] = useState(appointment);

    const formattedDate = 
        date.getFullYear() + '-' +
        String(date.getMonth() + 1).padStart(2, '0') + '-' +
        String(date.getDate()).padStart(2, '0');

    const [currentDateTime, setCurrentDateTime] = useState(formattedDate);

    useEffect(() => {
        reloadAppointment();
    }, [requestedCancel, scheduledCancel, reschedule]);

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

    const date_diff_indays = function (date1, date2) {
        const dt1 = new Date(date1);
        const dt2 = new Date(date2);
        return Math.floor(
            (Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate()) -
                Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate())) /
                    (1000 * 60 * 60 * 24)
        );
    };

    const handleDateInputChange = (selectedDate, selectedTime) => {
        const updatedData = {
            ...appointment,
            date: selectedDate, 
            time: selectedTime
        };
        console.log(updatedData)
        setUpdatedAppointment(updatedData)
    };

    const rescheduleAppointment = (appointmentData) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointment.appointmentId}/reschedule`, {
            method: "PUT",
            headers: {
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            },
            body: JSON.stringify(appointmentData)
        })
        .then((response) => {
            if (!response.ok) {
                console.error("Response status: " + response.status);
                throw new Error('Network response was not ok ' + response.statusText);
            }
            console.log("Appointment rescheduled");
        })
        .catch((error) => {
            console.error("Error message:", error.message);
        });
    }


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
                <button name="Details" className="view-details-button" onClick={() => setShow(!show)}>{t('appointmentViewDetails')}</button>
                {appointment.status === "REQUESTED" && 
                <button name="RequestedCancel" className="view-details-button" onClick={() => setRequestedCancel(!requestedCancel)}>{t('appointmentCancel')}</button>}
                {appointment.status === "SCHEDULED" && 
                <button name="ScheduleCancel" className="view-details-button" onClick={() => setScheduledCancel(!scheduledCancel)}>{t('appointmentCancel')}</button>}
                {appointment.status === "SCHEDULED" && 
                <button name="Rescedule" className="view-details-button" onClick={() => setReschedule(!reschedule)}>{t('appointmentReschedule')}</button>}
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
                <button name="confirm" className="view-details-button" onClick={() => {
                    setRequestedCancel(false);
                    cancelAppointment(appointment.appointmentId);
                    alert(t('appointmentCancelled'));
                    }}>{t('appointmentConfirm')}</button>
                <button name="Close" className="view-details-button" onClick={() => setRequestedCancel(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>
        </Modal>

        <Modal show={scheduledCancel} onHide={() => setScheduledCancel(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{t('cancelAppointment')}</Modal.Title>
            </Modal.Header>

            <Modal.Body>{t('appointmentCancelMessage')}</Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button name="confirm" className="view-details-button" onClick={() => {
                    {   
                        const currentDatef = format(new Date(formattedDate), "yyyy-MM-dd");
                        const appointmentDate = format(new Date(appointment.date), "yyyy-MM-dd");
  
                        if (date_diff_indays(currentDatef, appointmentDate) > 2) {
                            cancelAppointment(appointment.appointmentId);
                            setScheduledCancel(false);
                            alert(t('appointmentCancelled'));
                          } else {
                            alert(t('invalidCancel'));
                            setScheduledCancel(false);
                        }
                    }
                    }}>{t('appointmentConfirm')}</button>
                <button name="Close" className="view-details-button" onClick={() => setScheduledCancel(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>
        </Modal>

        <Modal className="custom-modal" show={reschedule} onHide={() => setReschedule(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{t('Reschedule Appointment')}</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <AvailabilitiesCalendar onChange={(selectedDate, selectedTime) => handleDateInputChange(selectedDate, selectedTime)} />
            </Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button name="confirm" className="view-details-button" onClick={() => {
                    {   
                        const currentDatef = format(new Date(formattedDate), "yyyy-MM-dd");
                        const appointmentDate = format(new Date(appointment.date), "yyyy-MM-dd");
  
                        if (date_diff_indays(currentDatef, appointmentDate) > 2) {
                            rescheduleAppointment(updatedAppointment);
                            setReschedule(false);
                            alert(t('appointmentRescheduled'));
                          } else {
                            alert(t('invalidCancel'));
                            setReschedule(false);
                        }
                    }
                    }}>{t('appointmentConfirm')}</button>
                <button name="Close" className="view-details-button" onClick={() => setReschedule(false)}>{t('appointmentClose')}</button>
            </Modal.Footer>

        </Modal>
    </div>
  );
}

export default Appointment;