import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './TrainerAccounts.css';
import { Modal } from 'react-bootstrap';
import AddServiceButton from "../../components/PersonalTrainerPanel/addService";
import "../../css/style.css";
import { useTranslation } from "react-i18next";

function Services() {

    const {
        isAuthenticated,
    } = useAuth0();


    const [services, setServices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const { t } = useTranslation('adminPanel');

    useEffect(() => {
        getAllFitnessServices();
    }, []);

    const getAllFitnessServices = () => {
        fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/fitnessPackages`, {
            method: "GET",
            headers: new Headers({
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
                setServices(data);
            })
            .catch((error) => {
                console.log(error);
            });
    };

    const [fitnessDataToSend, setFitnessDataToSend] = useState({});
    const [durationType, setDurationType] = useState('minutes');
    const [showForm, setShowForm] = useState(false);
    
    const handleInputChange = (e) => {
      const {name, value} = e?.target || {};
      const updatedData = {
        ...fitnessDataToSend,
        [name]: value,
    };
    setFitnessDataToSend(updatedData);
    };

    const handlePriceChange = (e) => {
      const {name, value} = e?.target || {};
      const doubleValue = parseFloat(value);
      const updatedData = {
        ...fitnessDataToSend,
        [name]: doubleValue,
    };
    setFitnessDataToSend(updatedData);
    };

    const handleDurationChange = (e) => {
      const { name, value } = e.target;

      if (name === 'durationType'){
        setDurationType(value);
      }
      
      const updatedData = {
        ...fitnessDataToSend,
        duration: name === 'duration' ? `${value} ${durationType}` : `${fitnessDataToSend.duration} ${value}`
      };
      setFitnessDataToSend(updatedData);
    };


    return (
        <div>

            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}

            <div className="accounts-section">
                <div className="container">
                    <Link to="/trainerPanel" className="button back-button">{t('back')}</Link>
                    <div className="header-section">
                        <h1>{t('fitnessService')}</h1>
                        <button onClick={() => setShowForm((prevShowForm)=> !prevShowForm)} className="add-button">
                          <xml version="1.0" encoding="utf-8"/>
                            <svg width="40px" height="40px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                             <path d="M4 12H20M12 4V20" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </button>
                    </div>
                    <div className="table-responsive">
                        <table className="table">
                            <thead>
                            <tr>
                                <th>{t('serviceName')}</th>
                                <th>{t('description')}</th>
                                <th>{t('duration')}</th>
                                <th>{t('otherInformation')}</th>
                                <th>{t('price')}</th>

                            </tr>
                            </thead>
                            <tbody>
                            {services.map(service => (
                                <tr key={service.id}>
                                    <td>{service.title}</td>
                                    <td>{service.description}</td>
                                    <td>{service.duration}</td>
                                    <td className="other-info">{service.otherInformation}</td>
                                    <td>{service.price}$</td>
                                    <td>
                                        <button className="button details-button">{t('edit')}</button>
                                        <button className="button delete-button">{t('delete')}</button>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <Modal show={showForm} onHide={()=>setShowForm(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add a Fitness Service</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <form>
          <div className="form-group">
            <input type="text" id="title" maxLength="50" placeholder={t('fitnessServiceTitle')} name="title" required  onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
          <input type="number" id="duration" max="99" placeholder={t('duration')} name="duration" required  onChange={(e) => handleDurationChange(e)} />
            <select id="durationType" name="durationType"  onChange={(e) => setDurationType(e.target.value)} required>
                <option value="minutes">minutes</option>
                <option value="hour(s)">hour(s)</option>
            </select>
          </div>
          <div className="form-group">
            <input type="number" id="price"  placeholder="Price" name={t('price')} required onChange={(e) => handlePriceChange(e)} />
          </div>
          <div className="form-group">
            <textarea id="description"  placeholder="Description" name={t('description')} required onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
            <textarea id="otherInformation"  placeholder={t('otherInformationOptional')} name="otherInformation" onChange={(e) => handleInputChange(e)} />
          </div>
    

        </form>
        <AddServiceButton fitnessDataToSend={fitnessDataToSend}/>
        </Modal.Body>

    </Modal>
            
        </div>



    );
}

export default Services;
