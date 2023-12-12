import React from 'react';
import ReactDOM from 'react-dom/client';

import './index.css';
import './App.js';
import './components/booking/bookingAppointment.js';
import './components/clientProfile/clientCoachNotes.js';
import './components/home/home.js';
import './components/clientProfile/clientInvoices.js';
import './components/layout/layout.js';
import './components/feedback/personalTrainerFeedback.js';
import './components/clientProfile/profile.js';
import './components/authentication/register.js';
import 'bootstrap/dist/css/bootstrap.min.css';



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
