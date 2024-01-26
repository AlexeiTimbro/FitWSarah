import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import './Invoices.css';
import {useGetAccessToken} from "../authentication/authUtils"; // Make sure to create this CSS file

function ClientInvoices({ accountId }) {
    const [invoices, setInvoices] = useState([]);
    const [accessToken, setAccessToken] = useState('');
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
            fetchInvoices("uuid-acc1");
        }
    }, [accessToken, accountId]);

    function fetchInvoices(accountId) {
        // Using mock data endpoint as the base URL may not be set
        const mockUrl = `http://localhost:8080/api/v1/accounts/${accountId}/invoices`;

        fetch(mockUrl, {
            method: 'GET',
            headers: new Headers({
                Authorization: 'Bearer ' + accessToken,
                'Content-Type': 'application/json',
            }),
        })
            .then(response => response.json())
            .then(data => {
                setInvoices(data);
            })
            .catch(error => {
                console.error('Failed to fetch invoices:', error);
            });
    }

    return (
        <div className='invoices-container'>
            <h1>Invoices</h1>
            <div className='invoices-table'>
                <table>
                    <thead>
                    <tr>
                        <th>Status</th>
                        <th>Amount</th>
                        <th>Content</th>
                        <th>Date</th>
                        <th>Due Date</th>
                        <th>Client</th>
                        <th>Payment Type</th>
                        <th>Price</th>
                    </tr>
                    </thead>
                    <tbody>
                    {invoices.map((invoice, index) => (
                        <tr key={index}>
                            <td>{invoice.status}</td>
                            <td>{invoice.amount}</td>
                            <td>{invoice.content}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ClientInvoices;
