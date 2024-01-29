import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';
import './TrainerCreateInvoice.css';
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { FaSearch } from 'react-icons/fa';
import { parse, format } from 'date-fns';
import { DateTimePicker } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import TextField from '@mui/material/TextField';
import { LocalizationProvider } from '@mui/x-date-pickers';
import { zonedTimeToUtc, utcToZonedTime } from 'date-fns-tz';
import { useTranslation } from "react-i18next";


function TrainerCreateInvoices() {
    const { isAuthenticated } = useAuth0();
    const navigate = useNavigate();
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [accounts, setAccounts] = useState([]);
    const [newInvoice, setNewInvoice] = useState({
        accountId: '',
        userId: '',
        username: '',
        status: 'PENDING',
        date: new Date(),
        dueDate: new Date(),
        paymentType: '',
        price: 0
    });

    const { t } = useTranslation('trainerCreateInvoice');


    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            if (token) setAccessToken(token);
        };

        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            fetchAllAccounts();
        }
    }, [accessToken]);

    const fetchAllAccounts = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            if (!response.ok) throw new Error('Network response was not ok');
            setAccounts(data);
        } catch (error) {
            console.error("Error fetching accounts:", error);
        }
    };

    const handleInputChange = (e) => {
        setNewInvoice({ ...newInvoice, [e.target.name]: e.target.value });
    };

    const handleDueDateChange = (date) => {
        setNewInvoice({ ...newInvoice, dueDate: date });
    };

    const handleUsernameChange = (e) => {
        const selectedUsername = e.target.value;
        const selectedAccount = accounts.find(account => account.username === selectedUsername);
        setNewInvoice({
            ...newInvoice,
            username: selectedUsername,
            accountId: selectedAccount ? selectedAccount.accountId : '',
            userId: selectedAccount ? selectedAccount.userId : ''
        });
    };

    const createInvoice = async (event) => {
        event.preventDefault();
        const formattedDate = format(new Date(), "yyyy-MM-dd'T'HH:mm:ss");
        const formattedDueDate = format(newInvoice.dueDate, "yyyy-MM-dd'T'HH:mm:ss");
        const invoiceData = {
            accountId: newInvoice.accountId,
            userId: newInvoice.userId,
            username: newInvoice.username,
            status: newInvoice.status,
            date: formattedDate,
            dueDate: formattedDueDate,
            paymentType: newInvoice.paymentType,
            price: newInvoice.price
        };
        try {
            const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/invoices`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(invoiceData)
            });
            if (!response.ok) throw new Error('Network response was not ok');
            navigate("/trainerInvoices");
        } catch (error) {
            console.error('Error creating invoice:', error);
        }
    };

    return (
        <div className="admin-invoices">
            <form onSubmit={createInvoice}>
                {/* Username Dropdown */}

                <label htmlFor="username">{t('Username:')}</label>
                <select id="username" name="username" value={newInvoice.username} onChange={handleUsernameChange}>
                    {accounts.map(account => (
                        <option key={account.id} value={account.username}>{account.username}</option>
                    ))}
                </select>

                {/* Invoice Status */}
                <label htmlFor="status">{t('Status:')}</label>
                <select id="status" name="status" value={newInvoice.status} onChange={handleInputChange}>
                    <option value="PENDING">{t('PENDING')}</option>
                    <option value="COMPLETED">{t('COMPLETED')}</option>
                    <option value="OVERDUE">{t('OVERDUE')}</option>
                </select>

                {/* Due Date Picker */}
                <label htmlFor="dueDate">{t('Due Date:')}</label>
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DateTimePicker value={newInvoice.dueDate} onChange={handleDueDateChange} renderInput={(params) => <TextField {...params} />} />
                </LocalizationProvider>

                {/* Payment Type */}
                <label htmlFor="paymentType">{t('Payment Type:')}</label>
                <input type="text" id="paymentType" name="paymentType" value={newInvoice.paymentType} onChange={handleInputChange} />

                {/* Price */}
                <label htmlFor="price">{t('Price:')}</label>
                <input type="text" id="price" name="price" value={newInvoice.price} onChange={handleInputChange} />

                <button type="submit">{t('Create Invoice')}</button>
            </form>
        </div>
    );
}

export default TrainerCreateInvoices;
