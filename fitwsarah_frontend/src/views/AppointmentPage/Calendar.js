import React, { useState, useEffect } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './NewAppointment.css';
import {useGetAccessToken} from "../../components/authentication/authUtils";
import { useLanguage } from '../../LanguageConfig/LanguageContext';
import { useTranslation } from "react-i18next";

const AvailabilitiesCalendar = ({onChange}) => {
  const [date, setDate] = useState(new Date());
  const [time, setTime] = useState('');
  const [availabilities, setAvailabilities] = useState([]);
  const [noAvailabilities, setNoAvailabilities] = useState(false);
  const [accessToken, setAccessToken] = useState(null);
  const [loading, setLoading] = useState(false);
  const getAccessToken = useGetAccessToken();
  const { language } = useLanguage();
  const {t} = useTranslation('home')

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
    setLoading(true);
    const formattedDate = date.toLocaleDateString(getLocale(), { weekday: 'long' });
    fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/availabilities?dayOfWeek=${formattedDate}`,  {
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
        }).finally(() => {
          setLoading(false);
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
              {loading && <svg xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" viewBox="0 0 24 24"><path fill="currentColor" d="M12,1A11,11,0,1,0,23,12,11,11,0,0,0,12,1Zm0,19a8,8,0,1,1,8-8A8,8,0,0,1,12,20Z" opacity="0.25"/><path fill="currentColor" d="M12,4a8,8,0,0,1,7.89,6.7A1.53,1.53,0,0,0,21.38,12h0a1.5,1.5,0,0,0,1.48-1.75,11,11,0,0,0-21.72,0A1.5,1.5,0,0,0,2.62,12h0a1.53,1.53,0,0,0,1.49-1.3A8,8,0,0,1,12,4Z"><animateTransform attributeName="transform" dur="0.75s" repeatCount="indefinite" type="rotate" values="0 12 12;360 12 12"/></path></svg>}
                          {!loading && !noAvailabilities &&
                      <ul>
                        
                        {availabilities.slice().sort((a, b) => {
                            return a.time.localeCompare(b.time);
                            }).map((availability, index) => (
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
                    </ul>}
                    {!loading && noAvailabilities && <p>{t('noAvailabilities')} {date.toLocaleDateString(getLocale(), { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>}
                    <p className="date">{date.toLocaleDateString(getLocale(), { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>
                </div>
            </div>
        </div>
    </div>
</div>
  );
};

export default AvailabilitiesCalendar;
