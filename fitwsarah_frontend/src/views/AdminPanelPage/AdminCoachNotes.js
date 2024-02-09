import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminAccounts.css';
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";


function AdminCoachNotes() {

    const { isAuthenticated } = useAuth0();
    const [coachNotes, setCoachNotes] = useState([]);
    const [editMode, setEditMode] = useState(null);
    const [editFormData, setEditFormData] = useState({ content_EN: '', content_FR: '' });
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const { t } = useTranslation('adminCoachNotes');

    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            if (token) setAccessToken(token);
        };
        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getAllCoachNotes();
        }
    }, [accessToken]);

    const getAllCoachNotes = () => {

        fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/coachnotes`, {
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
                setCoachNotes(data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const handleEdit = (coachNote) => {
        setEditMode(coachNote.id);
        setEditFormData({ content_EN: coachNote.content_EN, content_FR: coachNote.content_FR });
    };

    const handleSave = async (coachNoteId) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/coachnotes/${coachNoteId}`, {
                method: "PUT",
                headers: new Headers({
                    Authorization: `Bearer ${accessToken}`,
                    "Content-Type": "application/json"
                }),
                body: JSON.stringify({
                    content_EN: editFormData.content_EN,
                    content_FR: editFormData.content_FR,
                })
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            await response.json();
            setEditMode(null);
            getAllCoachNotes();
        } catch (error) {
            console.error("Error updating coach note:", error);
        }
    };


    const handleCancel = () => {
        setEditMode(null);
    };



    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">{t('back')}</Link>
                    <div className="header-section">
                        <h1>{t('coachNotes')}</h1>
                    </div>
                    <Link to="/AdminCreateCoachNotes" className="button back-button">Create Coach Note</Link>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('coachNoteId')}</th>
                                <th>{t('userId')}</th>
                                <th>{t('username')}</th>
                                <th>{t('content')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {coachNotes.map(coachNotes => (
                                <tr key={coachNotes.id}>
                                    <td>{coachNotes.coachNoteId}</td>
                                    <td>{coachNotes.userId}</td>
                                    <td>{coachNotes.username}</td>
                                    <td>{coachNotes.content_EN}</td>
                                    <td>{coachNotes.content_FR}</td>
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

export default AdminCoachNotes;