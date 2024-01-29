import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminInvoices.css';
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";


function AdminInvoices() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [invoices, setInvoices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');
    const getAccessToken = useGetAccessToken();


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


    useEffect(() => {
        if (accessToken) {
            getAllInvoices();
        }
    }, [accessToken]);

    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">{t('back')}</Link>
                    <div className="header-section">
                        <h1>{t('invoices')}</h1>
                    </div>
                    <Link to="/CreateAdminInvoices" className="button back-button">Create Invoice</Link>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('InvoiceId')}</th>
                                <th>{t('AccountId')}</th>
                                <th>{t('Username')}</th>
                                <th>{t('Status')}</th>
                                <th>{t('Date')}</th>
                                <th>{t('Due Date')}</th>
                                <th>{t('Payment Type')}</th>
                                <th>{t('Price')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {invoices.map(invoice => (
                                <tr key={invoice.id}>
                                    <td>{invoice.invoiceId}</td>
                                    <td>{invoice.accountId}</td>
                                    <td>{invoice.username}</td>
                                    <td>{invoice.status}</td>
                                    <td>{invoice.date}</td>
                                    <td>{invoice.dueDate}</td>
                                    <td>{invoice.paymentType}</td>
                                    <td>{invoice.price}</td>
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

export default AdminInvoices;