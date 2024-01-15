import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { useState, useEffect } from 'react';
import "./Appointment.css";
import "./Account.css";
function AppointmentCard({ appointment, accessToken }) {

    const [fitnessService, setFitnessService] = useState([]);

    useEffect(() => {
        if (accessToken) {
            getFitnessServiceById(appointment.serviceId);
        }
    }, [accessToken]);

    const getFitnessServiceById = (serviceId) => {
        fetch(`http://localhost:8080/api/v1/fitnessPackages/${serviceId}`, {
            method: "GET",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json",
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error(
                        "Network response was not ok " + response.statusText
                    );
                }
                return response.json();
            })
            .then((data) => {
                console.log(data)
                setFitnessService(data);
            })
            .catch((error) => {
                console.error(
                    "Error fetching service details for serviceId",
                    ":",
                    error
                );
            });
    };

    return (
        <div className="scroll-container">
        <div className="box">
            <div className="appointment">
                <div className="overlap-group">
                    <div className="group">
                        <div className="card-title1">Cardio Workout</div>
                        <div className="card-detail1"> Duration: 1:00 pm</div>
                        </div>
                    </div>
                    <img className="rectangle1" />
                <Button className="view-detail-button">View Detail</Button>
                </div>
        </div>
        </div>
    );
}

export default AppointmentCard;
