import './index.css';
import './App.js';
import './components/booking/bookingButton.js';
import './components/clientProfile/clientInvoices.js';
import './components/clientProfile/setting.js';
import 'bootstrap/dist/css/bootstrap.min.css';
import './components/navigation/NotLoggedIn/navNotLoggedIn.js';
import './components/navigation/loggedIn/navLoggedIn.js';
import './components/footer/footer.js';
import './components/CoachNote/CoachNote.js';
import './LanguageConfig/i18n.js';



import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Auth0Provider } from '@auth0/auth0-react';

const root = createRoot(document.getElementById('root'));
root.render(
<Auth0Provider
    domain={process.env.REACT_APP_AUTH0_DOMAIN}
    clientId={process.env.REACT_APP_AUTH0_CLIENT_ID}
    authorizationParams={{
      redirect_uri: window.location.origin,
      audience: process.env.REACT_APP_AUTH0_AUDIENCE,
      scope: "read:users update:current_user_metadata openid profile email"
    }}
  >
    <App />
  </Auth0Provider>,
);
reportWebVitals();
