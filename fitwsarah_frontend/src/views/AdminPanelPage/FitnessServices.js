import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { Link } from 'react-router-dom';
import './AdminAccounts.css';
import { Modal } from 'react-bootstrap';
import AddServiceButton from "../../components/PersonalTrainerPanel/addService";
import "../../css/style.css";
import { useGetAccessToken } from "../../components/authentication/authUtils";



function Services() {

    const {
        isAuthenticated,
    } = useAuth0();


    const [services, setServices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);

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
                    <Link to="/adminPanel" className="button back-button">Back</Link>
                    <div className="header-section">
                        <h1>Fitness Services</h1>
                    </div>
                <div className="table-responsive">
                    <table className="table">
                        <thead>
                        <tr>
                            <th>Service Name</th>
                            <th>Description</th>
                            <th>Duration</th>
                            <th>Other Information</th>
                            <th>Price</th>
                            
                        </tr>
                        </thead>
                        <tbody>
                        {services.map(service => (
                            <tr key={service.id}>
                                <td>{service.title}</td>
                                <td>{service.description}</td>
                                <td>{service.duration}</td>
                                <td className="other-info">{service.otherInformation}</td>
                                <td>{service.price}</td>
                                <td>
                                    <button className="button details-button">Edit</button>
                                    <button className="button delete-button">Delete</button>
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
                <input type="text" id="title" maxLength="50" placeholder="Fitness Service Title" name="title" required  onChange={(e) => handleInputChange(e)} />
            </div>
            <div className="form-group">
            <input type="number" id="duration" max="99" placeholder="Duration" name="duration" required  onChange={(e) => handleDurationChange(e)} />
                <select id="durationType" name="durationType"   onChange={(e) => setDurationType(e.target.value)}  required>
                    <option value="minutes">minutes</option>
                    <option value="hour(s)">hour(s)</option>
                </select>
            </div>
            <div className="form-group">
                <input type="number" id="price"  placeholder="Price" name="price" required onChange={(e) => handlePriceChange(e)} />
            </div>
            <div className="form-group">
                <textarea id="description"  placeholder="Description" name="description" required onChange={(e) => handleInputChange(e)} />
            </div>
            <div className="form-group">
                <textarea id="otherInformation"  placeholder="Other Information (Optional)" name="otherInformation" onChange={(e) => handleInputChange(e)} />
            </div>
        

            </form>
                <AddServiceButton fitnessDataToSend={fitnessDataToSend}/>
            </Modal.Body>

    </Modal>
        </div>



    );
}

export default Services;
