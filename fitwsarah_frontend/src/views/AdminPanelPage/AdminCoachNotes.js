import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from "react-router-dom";
import "./AdminCoachNotes.css"; // Adjust your CSS if needed
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";

function AdminCoachNotes() {
    const { isAuthenticated } = useAuth0();
    const [coachNotes, setCoachNotes] = useState([]);
    const [editCoachNoteId, setEditCoachNoteId] = useState(null);
    const [editFormData, setEditFormData] = useState({
        content_EN: "",
        content_FR: "",
    });
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const { t } = useTranslation();

    useEffect(() => {
        const fetchToken = async () => {
            const token = await getAccessToken();
            setAccessToken(token);
        };
        fetchToken();
    }, [getAccessToken]);

    useEffect(() => {
        if (accessToken) {
            getAllCoachNotes();
        }
    }, [accessToken]);

    const getAllCoachNotes = async () => {
        try {
            const response = await fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/coachnotes`, {
                method: "GET",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    "Content-Type": "application/json",
                },
            });
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const data = await response.json();
            setCoachNotes(data);
        } catch (error) {
            console.error("Failed to fetch coach notes:", error);
        }
    };

    const handleEditClick = (coachNote) => {
        setEditCoachNoteId(coachNote.coachNoteId);
        setEditFormData({
            content_EN: coachNote.content_EN,
            content_FR: coachNote.content_FR,
        });
    };

    const handleSaveClick = async (coachNoteId) => {
        try {
            const response = await fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/coachnotes/${coachNoteId}`, {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${accessToken}`,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(editFormData),
            });

            if (!response.ok) {
                throw new Error("Network response was not ok");
            }

            setEditCoachNoteId(null);
            getAllCoachNotes();
        } catch (error) {
            console.error("Failed to save coach note:", error);
        }
    };

    const handleChange = (event) => {
        const { name, value } = event.target;
        setEditFormData({ ...editFormData, [name]: value });
    };

    const handleCancelClick = () => {
        setEditCoachNoteId(null);
    };

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn />}
            {isAuthenticated && <NavLoggedIn />}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/adminPanel" className="button back-button">{t("back")}</Link>
                    <div className="header-section">
                        <h1>{t("coachNotes")}</h1>
                    </div>
                    <Link to="/AdminCreateCoachNotes" className="button back-button">Create Coach Note</Link>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t("coachNoteId")}</th>
                                <th>{t("userId")}</th>
                                <th>{t("username")}</th>
                                <th>{t("content_EN")}</th>
                                <th>{t("content_FR")}</th>
                                <th>{t("actions")}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {coachNotes.map((coachNote) => (
                                <tr key={coachNote.coachNoteId}>
                                    <td>{coachNote.coachNoteId}</td>
                                    <td>{coachNote.userId}</td>
                                    <td>{coachNote.username}</td>
                                    <td>
                                        {editCoachNoteId === coachNote.coachNoteId ? (
                                            <input
                                                type="text"
                                                name="content_EN"
                                                value={editFormData.content_EN}
                                                onChange={handleChange}
                                            />
                                        ) : (
                                            coachNote.content_EN
                                        )}
                                    </td>
                                    <td>
                                        {editCoachNoteId === coachNote.coachNoteId ? (
                                            <input
                                                type="text"
                                                name="content_FR"
                                                value={editFormData.content_FR}
                                                onChange={handleChange}
                                            />
                                        ) : (
                                            coachNote.content_FR
                                        )}
                                    </td>
                                    <td>
                                        {editCoachNoteId === coachNote.coachNoteId ? (
                                            <>
                                                <button onClick={() => handleSaveClick(coachNote.coachNoteId)} className="blueButton">Save</button>
                                                <button onClick={handleCancelClick} className="cancelDelete">Cancel</button>
                                            </>
                                        ) : (
                                            <button onClick={() => handleEditClick(coachNote)} className="blueButton">Edit</button>
                                        )}
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AdminCoachNotes;
