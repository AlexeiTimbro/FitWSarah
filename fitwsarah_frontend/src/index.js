import React from 'react';
import ReactDOM from 'react-dom/client';

import './css/index.css';
import './App.js';
import './views/bookingAppointment.js';
import './views/clientCoachNotes.js';
import './components/home/home.js';
import './views/clientInvoices.js';
import './components/layout/layout.js';
import './views/personalTrainerFeedback.js';
import './views/profile.js';
import './components/authentication/register.js';

import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
