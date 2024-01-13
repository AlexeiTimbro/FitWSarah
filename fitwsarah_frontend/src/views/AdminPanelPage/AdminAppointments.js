import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminAppointment.css';
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";


function AdminAccounts() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [appointments, setAppointments] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();

    const [searchTerm, setSearchTerm] = useState([["appointmentid",""], ["accountid",""], ["status",""]]);

    const labels = ["Appointment ID", "Account ID", "Status"];

    useEffect(() => {
        const fetchToken = async () => {
          const token = await getAccessToken();
          if (token) setAccessToken(token);
        };
        fetchToken();
      }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getAppointments();
        }
    }, [accessToken, searchTerm]);

    const handleCancelAppointment = (appointmentId) => {
        const isConfirmed = window.confirm("Are you sure you want to cancel this appointment?");
        if (isConfirmed) {
            updateAppointmentStatus(appointmentId, 'cancelled');
        }
    };


    const getAppointments = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
          if (term[1]) {
              params.append(term[0], term[1]);
          }
        });

        fetch(`http://localhost:8080/api/v1/appointments${params.toString() && "?" + params.toString()}`, {
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
                getAppointments();
            })
            .catch((error) => {
                console.log(error);

            });
    }

    function onInputChange(label, value) {
        const newSearchTerm = searchTerm.map((term) => {
            if (term[0] === label.toLowerCase().replace(/\s+/g, '')) {
                if (label === "Status") {
                    console.log(value.toUpperCase());
                    return [term[0], value.toUpperCase()];
                }
                return [term[0], value];
            }
            return term;
        });
        setSearchTerm(newSearchTerm);
    }

    function clearFilters() {
        setSearchTerm([["accountid",""], ["username",""], ["email",""], ["city",""]]);
    }

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">Back</Link>
                    <div className="header-section">
                        <h1>Appointments</h1>
                        <Filter labels={labels} onInputChange={onInputChange} searchTerm={searchTerm} clearFilters={clearFilters}/>
                    </div>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>Appointment ID</th>
                                <th>Account ID</th>
                                <th>Availability Id</th>
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
                                    <td>{appointments.serviceId}</td>
                                    <td>{appointments.status}</td>
                                    <td>{appointments.location}</td>
                                    <td>
                                        <button className="button delete-button" onClick={() => handleCancelAppointment(appointments.appointmentId)}>
                                            Cancel Appointment
                                        </button>
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
    );
};

export default AdminAccounts;
