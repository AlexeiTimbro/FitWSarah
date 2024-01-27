    import { useState, useEffect } from "react";
    import { useAuth0 } from '@auth0/auth0-react';
    import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
    import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
    import { useNavigate } from 'react-router-dom';
    import { Link } from 'react-router-dom';
    import './AdminCreateInvoice.css';
    import Filter from "../../components/AdminPanel/Filter";
    import { useGetAccessToken } from "../../components/authentication/authUtils";
    import { FaSearch } from 'react-icons/fa';
    import { parse, format } from 'date-fns';
    import { DateTimePicker } from '@mui/x-date-pickers';
    import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
    import TextField from '@mui/material/TextField';
    import { LocalizationProvider } from '@mui/x-date-pickers';


    function AdminCreateInvoices() {

        const {
            isAuthenticated,
        } = useAuth0();

        const navigate = useNavigate();
        const [invoices, setInvoices] = useState([]);
        const [accessToken, setAccessToken] = useState(null);
        const getAccessToken = useGetAccessToken();
        const [accounts, setAccounts] = useState([]);


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

        useEffect(() => {
            if (accessToken) {
                getAllAccounts();
            }
        }, [accessToken]);

        const [newInvoice, setNewInvoice] = useState({
            accountId: '',
            username: '',
            status: 'PENDING',
            date: new Date(),
            dueDate: new Date(),
            paymentType: '',
            price: 0
        });

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
                accountId: selectedAccount ? selectedAccount.accountId : ''
            });
        };

        const getAllInvoices = () => {

            fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/invoices`, {
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

        const createInvoice = (event) => {
            event.preventDefault();

            const currentDateTime = new Date();

            const invoiceData = {
                accountId: newInvoice.accountId,
                username: newInvoice.username,
                status: newInvoice.status,
                date: currentDateTime,
                dueDate: newInvoice.dueDate,
                paymentType: newInvoice.paymentType,
                price: newInvoice.price
            };

            fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/invoices`, {
                method: "POST",
                headers: new Headers({
                    Authorization: "Bearer " + accessToken,
                    "Content-Type": "application/json"
                }),
                body: JSON.stringify(invoiceData)
            })
                .then((response) => {
                    if (response.ok) {
                        navigate("/adminInvoices"); // Redirect on success
                    } else {
                        throw new Error('Network response was not ok');
                    }
                })
                .catch((error) => {
                    console.error('Error creating invoice:', error);
                });
        };


        const getAllAccounts = () => {

            fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/accounts`, {
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
                    setAccounts(data);
                })
                .catch((error) => {
                    console.log(error);
                });
        };

        return (
            <div className="admin-invoices">
                <form onSubmit={createInvoice}>
                    {/* Account ID */}
                    {/* Username Dropdown */}
                    <label htmlFor="username">Username:</label>
                    <select
                        id="username"
                        name="username"
                        value={newInvoice.username}
                        onChange={handleUsernameChange}
                    >
                        {accounts.map(account => (
                            <option key={account.id} value={account.username}>{account.username}</option>
                        ))}
                    </select>

                    {/* Invoice Status */}
                    <label htmlFor="status">Status:</label>
                    <select
                        id="status"
                        name="status"
                        value={newInvoice.status}
                        onChange={handleInputChange}
                    >
                        <option value="PENDING">PENDING</option>
                        <option value="COMPLETED">COMPLETED</option>
                        <option value="OVERDUE">OVERDUE</option>
                    </select>

                    {/* Due Date Picker */}
                    <label htmlFor="dueDate">Due Date:</label>
                    <LocalizationProvider dateAdapter={AdapterDateFns}>
                        <DateTimePicker
                            value={newInvoice.dueDate}
                            onChange={handleDueDateChange}
                            renderInput={(params) => <TextField {...params} />}
                        />
                    </LocalizationProvider>

                    {/* Payment Type */}
                    <label htmlFor="paymentType">Payment Type:</label>
                    <input
                        type="text"
                        id="paymentType"
                        name="paymentType"
                        value={newInvoice.paymentType}
                        onChange={handleInputChange}
                    />

                    {/* Price */}
                    <label htmlFor="price">Price:</label>
                    <input
                        type="text"
                        id="price"
                        name="price"
                        value={newInvoice.price}
                        onChange={handleInputChange}
                    />

                    <button type="submit">Create Invoice</button>
                </form>
            </div>


        );}

    export default AdminCreateInvoices;
