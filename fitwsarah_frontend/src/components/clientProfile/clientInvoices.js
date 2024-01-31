import React, { useEffect, useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import './Invoices.css';
import { useGetAccessToken } from "../authentication/authUtils";
import { useTranslation } from "react-i18next";
import { useLanguage } from "../../LanguageConfig/LanguageContext";


function ClientInvoices({userId}) {

    const [invoices, setInvoices] = useState([]);
    const [accessToken, setAccessToken] = useState(false);
    const { t } = useTranslation('invoices');
    const { language } = useLanguage();
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
            getInvoicesByUserId(userId);
        }
    }, [accessToken]);

    function getInvoicesByUserId(userId) {
        fetch(`http://localhost:8080/api/v1/invoices/users/${userId}`, {
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
                setInvoices(data);
            })
            .catch((error) => {
                console.log(error);
            });
    };


    return (
        <div className='invoices-container'>
            <h1>Invoices</h1>
            <div className='invoices-table'>
                <table>
                    <thead>
                    <tr>
                        <th>Username</th>
                        <th>Status</th>
                        <th>Date</th>
                        <th>Due Date</th>
                        <th>Payment Type</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {invoices.map((invoice, index) => (
                        <tr key={index}>
                            <td>{invoice.username}</td>
                            <td>{invoice.status}</td>
                            <td>{invoice.date}</td>
                            <td>{invoice.dueDate}</td>
                            <td>{invoice.paymentType}</td>
                            <td>{invoice.price}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ClientInvoices;