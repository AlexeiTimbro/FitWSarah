import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerInvoices.css';
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";


function TrainerInvoices() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [invoices, setInvoices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);

    const getAccessToken = useGetAccessToken();
    
    const { t } = useTranslation('adminPanel');


    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            if (token) setAccessToken(token);
        };

        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getAllInvoices();
        }
    }, [accessToken]);

    const getAllInvoices = () => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/invoices`, {
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
                setInvoices(data);
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
                    <div className="header-section">
                        <h1>{t('invoice')}</h1>
                    </div>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('invoiceId')}</th>
                                <th>{t('accountId')}</th>
                                <th>{t('amount')}</th>
                                <th>{t('content')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {invoices.map(invoice => (
                                <tr key={invoice.id}>
                                    <td>{invoice.invoiceId}</td>
                                    <td>{invoice.accountId}</td>
                                    <td>{invoice.amount}</td>
                                    <td>{invoice.content}</td>
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

export default TrainerInvoices;
