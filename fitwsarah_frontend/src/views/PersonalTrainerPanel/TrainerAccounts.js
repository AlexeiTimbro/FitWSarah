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
import './TrainerAccounts.css';
import { FaSearch } from 'react-icons/fa';
import { useTranslation } from "react-i18next";



function AdminAccounts() {

    const {
        isAuthenticated,
        getAccessTokenSilently,
    } = useAuth0();

    const [accounts, setAccounts] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: process.env.REACT_APP_AUTH0_AUDIENCE,
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
            getAllAccounts();
        }
    }, [accessToken]);




    const getAllAccounts = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts`, {
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
                setAccounts(data);
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
                    <Link to="/trainerPanel" className="button back-button">{t('back')}</Link>
                    <h1>{t('accounts')}</h1>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('accountId')}</th>
                                <th>{t('username')}</th>
                                <th>{t('password')}</th>
                                <th>{t('email')}</th>
                                <th>{t('city')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {accounts.map(account => (
                                <tr key={account.id}>
                                    <td>{account.accountId}</td>
                                    <td>{account.username}</td>
                                    <td>{account.password}</td>
                                    <td>{account.email}</td>
                                    <td>{account.city}</td>
                                    <td>
                                        <button className="button delete-button">{t('delete')}</button>
                                        <button className="button details-button">{t('details')}</button>
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
