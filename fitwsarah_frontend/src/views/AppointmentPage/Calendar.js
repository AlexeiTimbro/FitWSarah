import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './NewAppointment.css';
import {useGetAccessToken} from "../../components/authentication/authUtils";
import { useLanguage } from '../../LanguageConfig/LanguageContext';

const AvailabilitiesCalendar = ({onChange}) => {
  const [date, setDate] = useState(new Date());
  const [time, setTime] = useState('');
  const [availabilities, setAvailabilities] = useState([]);
  const [noAvailabilities, setNoAvailabilities] = useState(false);
  const [accessToken, setAccessToken] = useState(null);
  const getAccessToken = useGetAccessToken();
  const { language } = useLanguage();

  const getLocale = () => {
    return language === 'en' ? 'en-CA' : 'fr-CA';
  };

  useEffect(() => {
    const fetchToken = async () => {
        const token = await getAccessToken();
        if (token) setAccessToken(token);
    };
    fetchToken();
}, [getAccessToken]);

  useEffect(() => {
    if (accessToken) {
        getAllAvailabilities();
    }
}, [accessToken, date]);


  const handleDateChange = (newDate) => {
    setDate(newDate);
    const formattedDate = newDate.toLocaleDateString(getLocale(), { year: 'numeric', month: '2-digit', day: '2-digit' });
    onChange(formattedDate, time)
  };

  const handleTimeChange = (e) => {
    const newTime = e.target.value;
    setTime(newTime);
    const formattedDate = date.toLocaleDateString(getLocale(), { year: 'numeric', month: '2-digit', day: '2-digit' });
    onChange(formattedDate, newTime);
  };

  const isSameDay = (date1, date2) => {
    return (
      date1.getFullYear() === date2.getFullYear() &&
      date1.getMonth() === date2.getMonth() &&
      date1.getDate() === date2.getDate()
    );
  };

  const highlightedDay = ({ date, view }) => {
    if (view === 'month') {
      const dayOfWeek = date.getDay();
      if (dayOfWeek === 0 || dayOfWeek === 6) {

        return 'past-day'; 
      }
      if(date < new Date() && !isSameDay(date, new Date())){
        return 'past-day'
        } 
    }
    return null;
  };

  const getAllAvailabilities = () => {
    const formattedDate = date.toLocaleDateString(getLocale(), { weekday: 'long' });
    fetch(`${process.env.REACT_APP_DEVELOPMENT_URL}/api/v1/availabilities?dayOfWeek=${formattedDate}`,  {
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
            console.log(data);
            if(data.length == 0){
            setNoAvailabilities(true);
            }else{
              setNoAvailabilities(false);
              setAvailabilities(data);
            }
        })
        .catch((error) => {
            console.log(error);
        });
};
  
  return (
<div>
    <div className="calendar-container">
        <div>
        <Calendar onChange={handleDateChange} value={date} tileClassName={highlightedDay}  navigation={false} locale={getLocale()}/>
        </div>
        <div className='time-picker'>
            <div>
            <div class="focusable time-picker" tabindex="0" role="group">
                      <ul>
                        {availabilities.map((availability, index) => (
                            <li key={index}>
                                <input
                                    type="radio"
                                    id={`timeslot_${index}`}
                                    name="selectedTimeSlot"
                                    onChange={handleTimeChange}
                                    value={`${availability.time}`}
                                />
                                <label htmlFor={`timeslot_${index}`}>
                                    <span>{availability.time}</span><br/>
                                </label>
                            </li>
                        ))}
                    </ul>
                    {noAvailabilities && <p>No available dates for {date.toLocaleDateString(getLocale(), { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>}
                    <p className="date">{date.toLocaleDateString(getLocale(), { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>
                </div>
            </div>
        </div>
    </div>
</div>
  );
};

export default AvailabilitiesCalendar;
