import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import LoginButton from "../../components/authentication/login";
import LogoutButton from "../../components/authentication/logout";
import axios from 'axios';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import { Container, Row, Col, Modal, Button } from 'react-bootstrap';
import './TrainerAppointments.css';
import { FaSearch } from 'react-icons/fa';



function AdminAccounts() {

    const {
        isAuthenticated,
        getAccessTokenSilently,
    } = useAuth0();

    const [appointments, setAppointments] = useState([]);
    const [accessToken, setAccessToken] = useState(null);

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: configData.audience,
                        scope: configData.scope,
                    });
                    setAccessToken(token);
                } catch (e) {
                    console.error(e.message);
                }
            };
            getAccessToken();
        }
    }, [getAccessTokenSilently, isAuthenticated]);

    useEffect(() => {
        if (accessToken) {
            getAllAppointments();
        }
    }, [accessToken]);




    const getAllAppointments = () => {
        fetch("http://localhost:8080/api/v1/appointments", {
            method: "GET",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            })
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                setAppointments(data);
            })
            .catch((error) => {
                console.log(error);

            });
    };


    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div className="accounts-section">
                <div className="container">
                    <Link to="/trainerPanel" className="button back-button">Back</Link>
                    <h1>Appointments</h1>
                    <input
                        type="text"
                        className="search-bar"
                        placeholder="Search..."
                    />
                    <FaSearch className="search-icon" />
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>Appointment Id</th>
                                <th>Account Id</th>
                                <th>Availability Id</th>
                                <th>Admin Id</th>
                                <th>Service Id</th>
                                <th>Status</th>
                                <th>Location</th>
                            </tr>
                            </thead>
                            <tbody>
                            {appointments.map(appointments => (
                                <tr key={appointments.id}>
                                    <td>{appointments.appointmentId}</td>
                                    <td>{appointments.accountId}</td>
                                    <td>{appointments.availabilityId}</td>
                                    <td>{appointments.adminId}</td>
                                    <td>{appointments.serviceId}</td>
                                    <td>{appointments.status}</td>
                                    <td>{appointments.location}</td>
                                    <td>
                                        <button className="button delete-button">Cancel Appointment</button>
                                        <button className="button details-button">Appointment Details</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    );}

export default AdminAccounts;
