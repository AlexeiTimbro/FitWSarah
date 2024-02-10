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
    const { t } = useTranslation('adminPanel');
    const getAccessToken = useGetAccessToken();
    const [searchTerm, setSearchTerm] = useState([["dayOfWeek","Monday"]]);


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
    const handleDayChange = (event) => {
        setSearchTerm([["dayOfWeek", `${event.target.value}`]]);
        getAllAvailabilities();
    };

    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div>
                <div className="select-container">
                    <select onChange={handleDayChange}>
                        <option value="Monday">{t('monday')}</option>
                        <option value="Tuesday">{t('tuesday')}</option>
                        <option value="Wednesday">{t('wednesday')}</option>
                        <option value="Thursday">{t('thursday')}</option>
                        <option value="Friday">{t('friday')}</option>
                        <option value="Saturday">{t('saturday')}</option>
                        <option value="Sunday">{t('sunday')}</option>
                    </select>
                </div>
                <div className="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>{t('time')}</th>
                            </tr>
                        </thead>
                        <tbody>
                            {availabilities.map((availability) => (
                                <tr key={availability.id}>
                                    <td>{availability.time}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>



    );}

export default Availabilities;
