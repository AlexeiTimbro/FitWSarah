import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import './Availabilities.css';
import {useGetAccessToken} from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";



function Availabilities() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [availabilities, setAvailabilities] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const [newTime, setNewTime] = useState(null);
    const { t } = useTranslation('adminPanel');
    const getAccessToken = useGetAccessToken();
    const [searchTerm, setSearchTerm] = useState([["dayOfWeek","Monday"]]);
    const dayOfWeek = searchTerm[0][1];

    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            if (token) setAccessToken(token);
        };
        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getAllAvailabilities();
        }
    }, [accessToken, searchTerm]);


    const getAllAvailabilities = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
        if (term[1]) {
            params.append(term[0], term[1]);
        }
    });

        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/availabilities${params.toString() && "?" + params.toString()}`, {
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
                setAvailabilities(data);
            })
            .catch((error) => {
                console.log(error);
            });
    };
    
    const availabilityToSend = {time: newTime, dayOfWeek:dayOfWeek}
    const addAvailability = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
        if (term[1]) {
            params.append(term[0], term[1]);
        }
        });
        

        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/availabilities/add${params.toString() && "?" + params.toString()}`, {
            method: "POST",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            }),body: JSON.stringify(availabilityToSend)
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                console.log(response);
                return response.json();
            })
            .then((data) => {
                console.log(data);
                getAllAvailabilities();
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const deleteAvailability = async (availabilityId) => {
        
        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/availabilities/${availabilityId}`, {
            method: "DELETE",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            })
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
            })
            .then(() => {
                getAllAvailabilities();
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleDayChange = (event) => {
        setSearchTerm([["dayOfWeek", `${event.target.value}`]]);
        getAllAvailabilities();
    };
    const handleTimeChange = (value) => {
        setNewTime(`${value}`);
    };

    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div>
                <div className="table-container">
                    <h1>{t('availabilitiesTitle')}</h1>
                    <table>
                        <thead>
                            <tr>
                                <div className="select-container">
                                    <select onChange={handleDayChange}>
                                        <option value="Monday">{t('monday')}</option>
                                        <option value="Tuesday">{t('tuesday')}</option>
                                        <option value="Wednesday">{t('wednesday')}</option>
                                        <option value="Thursday">{t('thursday')}</option>
                                        <option value="Friday">{t('friday')}</option>
                                    </select>
                                </div>
                                <input
                                    type="time"
                                    id="time"
                                    name="time"
                                    pattern="[0-9]{2}:[0-9]{2}" 
                                    placeholder="Enter Time (HH:MM)"
                                    value={newTime}
                                    onChange={(e) => handleTimeChange(e.target.value)}
                                />
                                <button className="availabilityBtn" onClick={addAvailability}>+</button>
                            </tr>
                            <tr>
                                <th>{t('time')}</th>
                                <th>{t('removeBtn')}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {availabilities.slice().sort((a, b) => {
                            return a.time.localeCompare(b.time);
                            }).map((availability) => (
                                <tr key={availability.id}>
                                    <td>{availability.time}</td>
                                    <td><button className="availabilityBtn" onClick={() => deleteAvailability(availability.availabilityId)}>-</button></td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>



    );}

export default Availabilities;
