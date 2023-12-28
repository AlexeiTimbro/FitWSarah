import React from 'react';
import { Card, Button } from 'react-bootstrap';
import { useState, useEffect } from 'react';
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
        <Card className="appointment-card">
            <Card.Img variant="top" src={null} alt="Appointment Image" />
            <Card.Body className="appointment-card-body">
                <Card.Title className="appointment-card-title">{fitnessService.title}</Card.Title>
                <Card.Text className="appointment-card-detail">{fitnessService.duration}</Card.Text>
                <Button className="view-detail-button">View Detail</Button>
            </Card.Body>
        </Card>

    );
}

export default AppointmentCard;
