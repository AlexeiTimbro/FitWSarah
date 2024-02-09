import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminInvoices.css';
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";
import Filter from "../../components/AdminPanel/Filter";

function AdminInvoices() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [invoices, setInvoices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const [src] = useState('invoices')
    const { t } = useTranslation('filter');
    const getAccessToken = useGetAccessToken();
    const [searchTerm, setSearchTerm] = useState([["invoiceid",""], ["userid",""], ["username",""], ["status",""],  ["paymenttype",""]]);
    const labels = ["Invoice ID","User ID", "Username", "Status",  "Payment Type"];

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
    }, [accessToken, searchTerm]);

    const getAllInvoices = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
            if (term[1]) {
                params.append(term[0], term[1]);
            }
        });

        fetch(`http://localhost:8080/api/v1/invoices${params.toString() && "?" + params.toString()}`, {
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


    function onInputChange(label, value) {
        const newSearchTerm = searchTerm.map((term) => {
            if (term[0] === label.toLowerCase().replace(/\s+/g, '')) {
                return [term[0], value];
            }
            return term;
        });
        setSearchTerm(newSearchTerm);
    }

    function clearFilters() {
        setSearchTerm([["invoiceid",""],["userid",""], ["username", ""], ["status",""],  ["paymenttype",""]]);
    }




    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">{t('back')}</Link>
                    <div className="header-section">
                        <h1>{t('invoices')}</h1>
                        <Filter src={src} labels={labels} onInputChange={onInputChange} searchTerm={searchTerm} clearFilters={clearFilters}/>
                    </div>
                    <Link to="/CreateAdminInvoices" className="button back-button">Create Invoice</Link>
                    <div className="table-responsive">

                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('InvoiceId')}</th>
                                <th>{t('UserId')}</th>
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
                                    <td>{invoice.userId}</td>
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