import React from 'react';
import "../../css/style.css";
import { Container, Row, Col, Button } from 'react-bootstrap';
import Profile from '../../views/ProfilePage/Account';
import AppointmentCard from '../../views/ProfilePage/AppointmentCard';
import { useState } from 'react';
import "../../css/Account.css"

function ProfileSideBar({ appointments, accessToken }) {
    return (
        <div className="scroll-container">
            {appointments.map((appointment, index) => (
                <AppointmentCard key={index} appointment={appointment} accessToken={accessToken}/>
            ))}
        </div>
    );
}

export default ProfileSideBar;
