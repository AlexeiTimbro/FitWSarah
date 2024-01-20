import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminAccounts.css'; 
import Filter from "../../components/AdminPanel/Filter";
import { useGetAccessToken } from "../../components/authentication/authUtils";



function AdminAccounts() {

    const {
        isAuthenticated,
      } = useAuth0();

    const [accounts, setAccounts] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const [searchTerm, setSearchTerm] = useState([["accountid",""], ["username",""], ["email",""], ["city",""]]);

    const getAccessToken = useGetAccessToken();

    const labels = ["Account ID", "Username", "Email", "City"];

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

    
    return (
        <div>

    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}

      <div className="accounts-section">
        <div className="container">
            <Link to="/adminPanel" className="button back-button">Back</Link>
            <div className="header-section">
              <h1>Accounts</h1>
              <Filter labels={labels} onInputChange={onInputChange} searchTerm={searchTerm} clearFilters={clearFilters}/>
            </div>
          <div className="table-responsive">
            <table className="table">
              <thead>
                <tr>
                  <th>Account ID</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>City</th>
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
                      <button className="button delete-button">Delete</button>
                      <button className="button details-button">Details</button>
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
