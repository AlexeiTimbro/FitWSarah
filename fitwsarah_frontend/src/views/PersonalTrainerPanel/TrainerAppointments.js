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

    const [editAppointmentId, setEditAppointmentId] = useState(null);
    const [editFormData, setEditFormData] = useState({
        status: '',
        location: '',
    });
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

    const handleEditClick = (appointment) => {
        fetch(`http://localhost:8080/api/v1/appointments/${appointment.appointmentId}`, {
            method: "GET",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            })
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                setEditAppointmentId(appointment.appointmentId);
                // Set form data with fetched values for status and location
                setEditFormData({
                    status: data.status, // Assuming status is already in the correct format
                    location: data.location,
                });
            })
            .catch((error) => {
                console.error('Error fetching appointment details:', error);
            });
    };


    const handleSaveClick = (appointmentId) => {
        // Assuming your API endpoint can handle the full update, not just the status
        fetch(`http://localhost:8080/api/v1/appointments/${appointmentId}`, {
            method: "PUT",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify(editFormData) // Send the updated form data
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(() => {
                getAllAppointments(); // Refresh the list of appointments
            })
            .catch((error) => {
                console.error('Error updating appointment:', error);
            });

        setEditAppointmentId(null); // Exit edit mode
    };

    const handleChange = (event, appointmentId) => {
        const fieldName = event.target.getAttribute("name");
        const fieldValue = event.target.value;
        const newFormData = { ...editFormData };
        newFormData[fieldName] = fieldValue;
        setEditFormData(newFormData);
    };

    const handleCancelAppointment = (appointmentId) => {
        const isConfirmed = window.confirm("Are you sure you want to cancel this appointment?");
        if (isConfirmed) {
            updateAppointmentStatus(appointmentId, 'cancelled');
        }
    };


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

    const updateAppointmentStatus = (appointmentId, status) => {
        fetch("http://localhost:8080/api/v1/appointments/" + appointmentId + "/status", {
            method: "PUT",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify(status)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                getAllAppointments();
            })
            .catch((error) => {
                console.log(error);

            });
    }


    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
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
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {appointments.map((appointment) => (
                                editAppointmentId === appointment.appointmentId ? (
                                    <tr key={appointment.appointmentId}>
                                        <td>
                                            <select
                                                name="status"
                                                value={editFormData.status}
                                                onChange={handleChange}
                                            >
                                                <option value="SCHEDULED">SCHEDULED</option>
                                                <option value="CANCELLED">CANCELLED</option>
                                                <option value="COMPLETED">COMPLETED</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input
                                                type="text"
                                                name="location"
                                                value={editFormData.location}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <button className="button" onClick={() => handleSaveClick(appointment.appointmentId)}>
                                                Save
                                            </button>
                                            <button className="button" onClick={() => setEditAppointmentId(null)}>
                                                Cancel
                                            </button>
                                        </td>
                                    </tr>
                                ) : (
                                    <tr key={appointment.appointmentId}>
                                        <td>{appointment.appointmentId}</td>
                                        <td>{appointment.accountId}</td>
                                        <td>{appointment.availabilityId}</td>
                                        <td>{appointment.adminId}</td>
                                        <td>{appointment.serviceId}</td>
                                        <td>{appointment.status}</td>
                                        <td>{appointment.location}</td>
                                        <td>
                                            <button className="button" onClick={() => handleEditClick(appointment)}>
                                                Edit
                                            </button>
                                            <button className="button" onClick={() => handleCancelAppointment(appointment.appointmentId)}>
                                                Cancel Appointment
                                            </button>
                                        </td>
                                    </tr>
                                )
                            ))}
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    );}

export default AdminAccounts;
