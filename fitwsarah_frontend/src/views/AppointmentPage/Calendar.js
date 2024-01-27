import React, { useState } from 'react';
import Calendar from 'react-calendar';
import 'react-calendar/dist/Calendar.css';
import './NewAppointment.css';
import { useLanguage } from '../../LanguageConfig/LanguageContext';

const AvailabilitiesCalendar = ({onChange}) => {
  const [date, setDate] = useState(new Date());
  const [time, setTime] = useState('');
  const { language } = useLanguage();

  const getLocale = () => {
    console.log(language);
    return language === 'en' ? 'en-CA' : 'fr-CA';
  };

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
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_0" name="selectedTimeSlot" onChange={handleTimeChange} value="09:00"/>
                            <label class="" for="timeslot_0" tabindex="-1"><span>09:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_1" name="selectedTimeSlot" onChange={handleTimeChange} value="10:00"/>
                            <label class="" for="timeslot_1" tabindex="-1"><span>10:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_2" name="selectedTimeSlot" onChange={handleTimeChange} value="11:00"/>
                            <label class="" for="timeslot_2" tabindex="-1"><span>11:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_3" name="selectedTimeSlot" onChange={handleTimeChange} value="12:00"/>
                            <label class="" for="timeslot_3" tabindex="-1"><span>12:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_4" name="selectedTimeSlot" onChange={handleTimeChange} value="13:00"/>
                            <label class="" for="timeslot_4" tabindex="-1"><span>13:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_5" name="selectedTimeSlot" onChange={handleTimeChange} value="14:00"/>
                            <label class="" for="timeslot_5" tabindex="-1"><span>14:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_6" name="selectedTimeSlot" onChange={handleTimeChange} value="15:00"/>
                            <label class="" for="timeslot_6" tabindex="-1"><span>15:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_7" name="selectedTimeSlot" onChange={handleTimeChange} value="16:00"/>
                            <label class="" for="timeslot_7" tabindex="-1"><span>16:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_8" name="selectedTimeSlot" onChange={handleTimeChange} value="17:00"/>
                            <label class="" for="timeslot_8" tabindex="-1"><span>17:00</span><br/></label>
                        </li>
                        <li class="" >
                            <input type="radio" tabindex="-1" id="timeslot_9" name="selectedTimeSlot" onChange={handleTimeChange} value="18:00"/>
                            <label class="" for="timeslot_9" tabindex="-1"><span>18:00</span><br/></label>
                        </li>
                    </ul>
                    <p className="date">{date.toLocaleDateString(getLocale(), { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</p>
                </div>
            </div>
        </div>
    </div>
</div>
  );
};

export default AvailabilitiesCalendar;
