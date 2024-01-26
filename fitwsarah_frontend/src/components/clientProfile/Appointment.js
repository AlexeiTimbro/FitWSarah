import React, { useEffect, useState } from "react";
import { Modal } from "react-bootstrap";
import "./Appointment.css";


function Appointment( {appointment, accessToken} ) {

    const [service, setService] = useState(null);
    const [show, setShow] = useState(false);

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
                        <div className="card-title">{service?.title}</div>
                        <div className="card-detail">Location: {appointment.location}</div>
                        <div className="card-detail">Date: {appointment.date}</div>
                        <div className="card-detail">Time: {appointment.time}</div>
                    </div>
                </div>
                <button className="view-details-button" onClick={() => setShow(!show)}>View Detail</button>
            </div>
        </div>
        <Modal show={show} onHide={() => setShow(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{service?.title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>Description: {service?.description}</Modal.Body>
            <Modal.Body>Other Information: {service?.otherInformation}</Modal.Body>
            <Modal.Body>Price: {service?.price}$</Modal.Body>
            <Modal.Body>Duration: {service?.duration}</Modal.Body>
            <Modal.Body>Location: {appointment.location}</Modal.Body>
            <Modal.Body>Date: {appointment.date}</Modal.Body>
            <Modal.Body>Time: {appointment.time}</Modal.Body>

            <Modal.Footer style={{textAlign: 'right'}}>
                <button className="view-details-button" onClick={() => setShow(false)}>Close</button>
            </Modal.Footer>
        </Modal>
    </div>
  );
}

export default Appointment;