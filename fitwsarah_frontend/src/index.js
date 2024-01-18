import './index.css';
import './App.js';
import './components/booking/bookingButton.js';
import './components/clientProfile/clientCoachNotes.js';
import './components/home/home.js';
import './components/clientProfile/clientInvoices.js';
import './components/feedback/personalTrainerFeedback.js';
import './components/clientProfile/profile.js';
import './components/clientProfile/sidebar.js';
import './components/clientProfile/setting.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './components/navigation/NotLoggedIn/navNotLoggedIn.js';
import './components/navigation/loggedIn/navLoggedIn.js';
import './components/footer/footerNotLoggedIn/footerNotLoggedIn.js';
import './components/footer/footerLoggedIn/footerLoggedIn.js';




import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Auth0Provider } from '@auth0/auth0-react';

const root = createRoot(document.getElementById('root'));
root.render(
<Auth0Provider
    domain="dev-twa7h1nv0usycyum.us.auth0.com"
    clientId="G48IOcWYllMsX3UPo5jTRkC2mUkd4LJ5"
    authorizationParams={{
      redirect_uri: "https://fitwsarah.onrender.com/",
      audience: "https://dev-twa7h1nv0usycyum.us.auth0.com/api/v2/",
      scope: "read:users update:current_user_metadata openid profile email"
    }}
  >
    <App />
  </Auth0Provider>,
);
// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
