import { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import './AdminCreateCoachNotes.css';
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";

function AdminCreateCoachNotes() {
    const navigate = useNavigate();
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [accounts, setAccounts] = useState([]);
    const [newCoachNote, setNewCoachNote] = useState({
        accountId: '', // Added to hold the selected account's ID
        userId: '',
        username: '',
        content_EN: '',
        content_FR: '',
    });

    const { t } = useTranslation('trainerCreateCoachNotes');

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
        setNewCoachNote({ ...newCoachNote, [e.target.name]: e.target.value });
    };

    const handleUsernameChange = (e) => {
        const selectedUsername = e.target.value;
        const selectedAccount = accounts.find(account => account.username === selectedUsername);
        setNewCoachNote({
            ...newCoachNote,
            username: selectedUsername,
            accountId: selectedAccount ? selectedAccount.accountId : '',
            userId: selectedAccount ? selectedAccount.userId : ''
        });
    };

    const createCoachNote = async (event) => {
        event.preventDefault();
        const coachNoteData = {
            accountId: newCoachNote.accountId,
            userId: newCoachNote.userId,
            username: newCoachNote.username,
            content_EN: newCoachNote.content_EN,
            content_FR: newCoachNote.content_FR,
        };
        try {
            const response = await fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/coachnotes`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(coachNoteData)
            });
            if (!response.ok) throw new Error('Network response was not ok');
            navigate("/adminCoachNotes");
        } catch (error) {
            console.error('Error creating coach note:', error);
        }
    };

    return (
        <div className="admin-invoices">
            <form onSubmit={createCoachNote}>
                {/* Username Dropdown */}
                <label htmlFor="username">{t('Username:')}</label>
                <select id="username" name="username" value={newCoachNote.username} onChange={handleUsernameChange}>
                    <option value="">Select a username</option>
                    {accounts.map(account => (
                        <option key={account.id} value={account.username}>{account.username}</option>
                    ))}
                </select>

                {/* Content (EN) */}
                <label htmlFor="content_EN">{t('Content (EN)')}</label>
                <textarea id="content_EN" name="content_EN" value={newCoachNote.content_EN} onChange={handleInputChange}></textarea>

                {/* Content (FR) */}
                <label htmlFor="content_FR">{t('Content (FR)')}</label>
                <textarea id="content_FR" name="content_FR" value={newCoachNote.content_FR} onChange={handleInputChange}></textarea>

                <button type="submit">{t('Create Coach Note')}</button>
            </form>
        </div>
    );
}

export default AdminCreateCoachNotes;
