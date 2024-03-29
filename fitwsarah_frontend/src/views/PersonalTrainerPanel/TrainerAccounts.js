import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerAccounts.css';
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import { useTranslation } from "react-i18next";



function AdminAccounts() {

    const {
        isAuthenticated,
    } = useAuth0();

    const [accounts, setAccounts] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');
    const [searchTerm, setSearchTerm] = useState([["accountid",""], ["username",""], ["email",""], ["city",""]]);
    const labels = ["Account ID", "Username", "Email", "City"];
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
              getAllAccounts();
          }
      }, [accessToken, searchTerm]);

    const handleDelete = (accountId) => {

        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts/${accountId}`, {
            method: "DELETE",
            headers: new Headers({
                Authorization: "Bearer " + accessToken,
                "Content-Type": "application/json"
            })
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
        })
        .then(() => {
            getAllAccounts();
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
        setSearchTerm([["accountid",""], ["username",""], ["email",""], ["city",""]]);
    }


    const getAllAccounts = () => {
        const params = new URLSearchParams();
        searchTerm.forEach(term => {
            if (term[1]) {
                params.append(term[0], term[1]);
            }
        });
    
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/accounts${params.toString() && "?" + params.toString()}`, {
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
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/trainerPanel" className="button back-button">{t('back')}</Link>
                    <h1>{t('accounts')}</h1>
                    <Filter labels={labels} onInputChange={onInputChange} searchTerm={searchTerm} clearFilters={clearFilters}/>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('accountId')}</th>
                                <th>{t('username')}</th>
                                <th>{t('email')}</th>
                                <th>{t('city')}</th>
                            </tr>
                            </thead>
                            <tbody>
                            {accounts.map(account => (
                                <tr key={account.id}>
                                    <td>{account.accountId}</td>
                                    <td>{account.username}</td>
                                    <td>{account.email}</td>
                                    <td>{account.city}</td>
                                    <td>
                                    <button className="button delete-button" onClick={() => handleDelete(account.accountId)}>{t('delete')}</button>
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

export default AdminAccounts;
