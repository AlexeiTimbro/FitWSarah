import logo from './logo.svg';
import './App.css';
import React, { useState, useEffect } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import configData from './config.json'
import FitnessServiceList from './views/HomePage/FitnessService';

function App() {
  const {
    isLoading,
    error,
    isAuthenticated,
    user,
    getAccessTokenSilently,
    loginWithRedirect,
    logout,
  } = useAuth0();

  const [accessToken, setAccessToken] = useState(null);

  useEffect(() => {
    if (isAuthenticated) {
      const getAccessToken = async () => {
        try {
          const token = await getAccessTokenSilently({
            audience: configData.audience,
            scope: configData.scope,
          });
          console.log("Access Token:", token);
          setAccessToken(token);
        } catch (e) {
          console.error(e.message);
        }
      };
      getAccessToken();
    }
  }, [getAccessTokenSilently, isAuthenticated]);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Oops... {error.message}</div>;
  }

  if (!isAuthenticated) {
    return <button onClick={loginWithRedirect}>Log in</button>;
  }

  return (
    <div className="App">
      {accessToken && <FitnessServiceList accessToken={accessToken}></FitnessServiceList>}
        <p>Hi {user.email}, You have successfully logged in.</p>

        <button onClick={() => logout({ returnTo: window.location.origin })}>
          Log Out
        </button>
    </div>
  );
}

export default App;
