import React from "react";
import { useTranslation } from "react-i18next";
import { useEffect, useState } from "react";
import { useGetAccessToken } from "../../components/authentication/authUtils";
import LineGraphics from "../../components/AdminPanel/LineGraphics";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import CircleGraphics from "../../components/AdminPanel/CircleGraphics";



function AdminAnalytics() {

    const { t } = useTranslation('adminPanel');

    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());
    const [accountData, setAccountData] = useState({'01': 0, '02': 0, '03': 0, '04': 0, '05': 0, '06': 0, '07': 0, '08': 0, '09': 0, '10': 0, '11': 0, '12': 0});
    const [appointmentData, setAppointmentData] = useState([0, 0, 0, 0])
    const dataValues = []

    useEffect(() => {
        const fetchToken = async () => {
          const token = await getAccessToken();
          if (token) setAccessToken(token);
        };
  
        fetchToken();
      }, [getAccessToken]);


    useEffect(() => {
        if (accessToken){
            getAccountsAnalytics();
            getAppointments();
        }
    }, [accessToken, selectedYear]);

    const getAccountsAnalytics = () => {
        fetch(`http://localhost:8080/api/v1/accounts`, {
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
        .then((accounts) => {
            setAccountData({'01': 0, '02': 0, '03': 0, '04': 0, '05': 0, '06': 0, '07': 0, '08': 0, '09': 0, '10': 0, '11': 0, '12': 0});
            accounts.map((account) => {
                if (account.dateCreated.substring(0, 4) == selectedYear) {
                    setAccountData(prevData => ({...prevData, [account.dateCreated.substring(5, 7)]: prevData[account.dateCreated.substring(5, 7)] + 1}));
                }
            });
        })
        .catch((error) => {
            console.log(error);
        });
    }

    const getAppointments = () => {
        fetch(`http://localhost:8080/api/v1/appointments`, {
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
        .then((appointments) => {
            console.log(appointments);
            setAppointmentData([0, 0, 0, 0]);
            appointments.map((appointment) => {
                if(appointment.date.substring(0, 4) == selectedYear){
                    if(appointment.status == 'REQUESTED'){
                        setAppointmentData(prevData => ([prevData[0] + 1, prevData[1], prevData[2], prevData[3]]));
                    }
                    if(appointment.status == 'SCHEDULED'){
                        setAppointmentData(prevData => ([prevData[0], prevData[1] + 1, prevData[2], prevData[3]]));
                    }
                    if(appointment.status == 'COMPLETED'){
                        setAppointmentData(prevData => ([prevData[0], prevData[1], prevData[2] + 1, prevData[3]]));
                    }
                    if(appointment.status == 'CANCELLED'){
                        setAppointmentData(prevData => ([prevData[0], prevData[1], prevData[2], prevData[3] + 1]));
                    }
                }
            });
        })
        .catch((error) => {
            console.log(error);
        });
    }

    return (
        <div style={{backgroundColor: '#f5f5f5' }}>
            <NavLoggedIn/>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <h1>Analytics</h1>
                <div>
                    <label>Year: </label>
                    <select title="Year" value={selectedYear} onChange={(e) => setSelectedYear(e.target.value)}>
                        <option value="2023">2023</option>
                        <option value="2024">2024</option>
                        <option value="2025">2025</option>
                        <option value="2026">2026</option>
                        <option value="2027">2027</option>
                        <option value="2028">2028</option>
                        <option value="2029">2029</option>
                        <option value="2030">2030</option>
                    </select>
                </div>
            </div>
            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px' }}>
                <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '10px', boxShadow: '0 0 10px rgba(0,0,0,0.1)' }}>
                    <LineGraphics title='Accounts' labels={["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]} datasetsLabels='Number of users' 
                    data={[accountData['01'], accountData['02'], accountData['03'], accountData['04'], accountData['05'], accountData['06'], accountData['07'], accountData['08'], accountData['09'], accountData['10'], accountData['11'], accountData['12']]} year={selectedYear} />
                </div>
                <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '10px', boxShadow: '0 0 10px rgba(0,0,0,0.1)' }}>
                    <CircleGraphics title='Appointments' year={selectedYear} labels={['Requested', 'Scheduled', 'Completed', 'Cancelled']} data={appointmentData} />
                </div>
            </div>
            <FooterNotLoggedIn/>
        </div>
    );
}

export default AdminAnalytics;