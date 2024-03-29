import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerAppointments.css';
import Filter from "../../components/AdminPanel/Filter";
import {useGetAccessToken} from "../../components/authentication/authUtils";
import { parse, format } from 'date-fns';
import { DateTimePicker } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import TextField from '@mui/material/TextField';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { useTranslation } from "react-i18next";


function AdminAccounts() {

    const {
        isAuthenticated,
    } = useAuth0();

    const { t } = useTranslation('adminPanel');
    const [appointments, setAppointments] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [src] = useState('appointment')
    const [searchTerm, setSearchTerm] = useState([["appointmentid",""], ["status",""]]);

    const labels = ["Appointment ID", "Status"];

    const [editAppointmentId, setEditAppointmentId] = useState(null);
    const [editFormData, setEditFormData] = useState({
        status: '',
        location: '',
        firstName: '',
        lastName: '',
        phoneNum: '',
        date: '',
        time: '',
    });
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

    const handleEditClick = (appointment) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointment.appointmentId}`, {
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
                const combinedDateTime = parse(`${data.date} ${data.time}`, 'yyyy-MM-dd HH:mm', new Date());
                setEditFormData({
                    status: data.status,
                    location: data.location,
                    firstName: data.firstName,
                    lastName: data.lastName,
                    phoneNum: data.phoneNum,
                    dateTime: combinedDateTime,
                });
            })
            .catch((error) => {
                console.error('Error fetching appointment details:', error);
            });
    };


    const handleSaveClick = (appointmentId) => {
        const fields = ['location', 'firstName', 'lastName', 'phoneNum'];
      for (const field of fields) {
        console.log(field, editFormData[field])
        if (!editFormData[field]) {
            alert(t('completeFields'));
          return;
        }
      }
    const phoneRegex = /^[0-9]{3}-[0-9]{3}-[0-9]{4}$/;
        if (!phoneRegex.test(editFormData.phoneNum)) {
            alert(t('phoneValid'));
            return;
        }
        
        const updatedAppointment = {
            ...editFormData,
            date: format(editFormData.dateTime, 'yyyy-MM-dd'),
            time: format(editFormData.dateTime, 'HH:mm'),
        };

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointmentId}`, {
            method: "PUT",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify(updatedAppointment)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(() => {
                getAppointments();
                setEditAppointmentId(null); 
            })
            .catch((error) => {
                console.error('Error updating appointment:', error);
            });
    };

    const handleChange = (event) => {
        const fieldName = event.target.name;
        const fieldValue = event.target.value;
        setEditFormData({
            ...editFormData,
            [fieldName]: fieldValue,
        });
    };


    const handleDateTimeChange = (dateTime) => {
        setEditFormData({
            ...editFormData,
            dateTime,
        });
    };


    const handleCancelAppointment = (appointmentId) => {
        const isConfirmed = window.confirm(t('areyousure_cancelApp'));
        if (isConfirmed) {
            updateAppointmentStatus(appointmentId, 'cancelled');
        }
    };

    const handleAcceptedAppointment = (appointmentId) => {
        const isConfirmed = window.confirm(t('areyousure_acceptApp'));
        if (isConfirmed) {
            handleAppointmentRequest(appointmentId, 'scheduled');
        }
    };


    const getAppointments = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
            if (term[1]) {
                params.append(term[0], term[1]);
            }
        });

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments${params.toString() && "?" + params.toString()}`, {
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
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointmentId}/cancelled`, {
            method: "PUT",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify(status)

        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
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


    const handleAppointmentRequest = (appointmentId, status) => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/appointments/${appointmentId}/scheduled`, {
            method: "PUT",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),
            body: JSON.stringify(status)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
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
        setSearchTerm([["appointmentid",""], ["status",""]]);
    }

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}
            <div className="accounts-section">
                <div className="container">
                    <Link to="/trainerPanel" className="button back-button">{t('back')}</Link>
                    <h1>{t('appointment')}</h1>
                    <div className="filter-container">
                        <Filter src={src} labels={labels} onInputChange={onInputChange} searchTerm={searchTerm} clearFilters={clearFilters}/>
                    </div>

                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('appointmentId')}</th>
                                <th>{t('status')}</th>
                                <th>{t('location')}</th>
                                <th>{t('firstName')}</th>
                                <th>{t('lastName')}</th>
                                <th>{t('phoneNumber')}</th>
                                <th>{t('date')}</th>
                                <th>{t('time')}</th>
                                <th>{t('actions')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {appointments.map((appointment) => (
                                editAppointmentId === appointment.appointmentId ? (
                                    <tr key={appointment.appointmentId}>
                                        <td>{appointment.appointmentId}</td>
                                        <td>
                                            <select
                                                name="status"
                                                value={editFormData.status}
                                                onChange={handleChange}
                                            >
                                                <option value="SCHEDULED">{t('scheduled')}</option>
                                                <option value="COMPLETED">{t('completed')}</option>
                                                <option value="CANCELLED">{t('cancelled')}</option>
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
                                            <input
                                                type="text"
                                                name="firstName"
                                                value={editFormData.firstName}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                type="text"
                                                name="lastName"
                                                value={editFormData.lastName}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td>
                                            <input
                                                type="tel"
                                                pattern="/^[0-9]{3}-[0-9]{3}-[0-9]{4}$/"
                                                name="phoneNum"
                                                value={editFormData.phoneNum}
                                                onChange={handleChange}
                                            />
                                        </td>
                                        <td className="date-time-picker-column">
                                            <LocalizationProvider dateAdapter={AdapterDateFns}>
                                                <DateTimePicker
                                                    label="Date & Time picker"
                                                    value={editFormData.dateTime}
                                                    onChange={handleDateTimeChange}
                                                    renderInput={(params) => <TextField {...params} />}
                                                />
                                            </LocalizationProvider>
                                        </td>
                                        <td className="button-container">
                                            <button className="saveButton" onClick={() => handleSaveClick(appointment.appointmentId)}>
                                                {t('save')}
                                            </button>
                                            <button className="cancelButton" onClick={() => setEditAppointmentId(null)}>
                                                {t('cancel')}
                                            </button>
                                        </td>
                                    </tr>
                                ) : (
                                    <tr key={appointment.appointmentId}>
                                        <td>{appointment.appointmentId}</td>
                                        <td>{appointment.status}</td>
                                        <td>{appointment.location}</td>
                                        <td>{appointment.firstName}</td>
                                        <td>{appointment.lastName}</td>
                                        <td>{appointment.phoneNum}</td>
                                        <td>{appointment.date}</td>
                                        <td>{appointment.time}</td>
                                        <td className="edit-button-container">
                                            {appointment.status === "REQUESTED" && (
                                                <>
                                                    <button className="acceptButton" onClick={() => handleAcceptedAppointment(appointment.appointmentId)}>
                                                        {t('accept')}
                                                    </button>
                                                    <button className="cancelButton" onClick={() => handleCancelAppointment(appointment.appointmentId)}>
                                                        {t('deny')}
                                                    </button>
                                                </>
                                            )}
                                            {appointment.status !== "CANCELLED" && appointment.status !== "REQUESTED" && (
                                                <>
                                                    <button className="saveButton" onClick={() => handleEditClick(appointment)}>
                                                        {t('edit')}
                                                    </button>
                                                    <button className="cancelButton" onClick={() => handleCancelAppointment(appointment.appointmentId)}>
                                                    {t('cancelAppointment')}
                                                    </button>
                                                </>
                                            )}
                                            {appointment.status === "CANCELLED" && (
                                                <button className="saveButton" onClick={() => handleEditClick(appointment)}>
                                                    {t('edit')}
                                                </button>
                                            )}
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
