import React from "react";
import { useTranslation } from "react-i18next";
import { Line } from 'react-chartjs-2'
import { Chart, registerables } from 'chart.js'
import { useEffect, useState } from "react";
import { useGetAccessToken } from "../../components/authentication/authUtils";
Chart.register(...registerables)

const accountsGraph = {
    labels: [
        'January',
        'February',
        'March',
        'April',
        'May',
        'June',
        'July',
        'August',
        'September',
        'October',
        'November',
        'December'
    ],
    datasets: [
      {
        label: 'Number Of Users',
        backgroundColor: [
          'Indigo'
        ],
        fill: false,
        lineTension: 0.5,
        borderColor: 'rgba(0,0,0,1)',
        borderWidth: 2,
        data: accountData
      },
    ],
  }


function AdminAnalytics() {

    const { t } = useTranslation('adminPanel');

    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [accountData, setAccountData] = useState([null, null, null, null, null, null, null, null, null, null, null, null])

    const dateMonth = new Date().getMonth();

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
        }
    }, [accessToken]);

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
        .then((data) => {
            const numberOfUsers = data.map((user) => user.count);
            
        })
        .catch((error) => {
            console.log(error);
        });
    }

    return (
        <div>
            <h1>Analytics</h1>
            <Line
            data={accountsGraph}
            options={{
                title: {
                display: true,
                text: 'Class Strength',
                fontSize: 20,
                },
                legend: {
                display: true,
                position: 'right',
                },
            }}
            />
        </div>
    );
}

export default AdminAnalytics;