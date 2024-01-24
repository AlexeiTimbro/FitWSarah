import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Row, Col, Modal } from 'react-bootstrap';
import "../../css/style.css";
import { Link } from 'react-router-dom';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import AddMemberProfile from "../authentication/Signup";
import { ROLES } from "../../components/authentication/roles";
import "../../components/authentication/switch.css"
import RoleBasedSwitch from "../../components/authentication/RoleBasedSwitch";
import RequestNewFitnessService from "../HomePage/newServiceBtn";
import AddServiceButton from "../../components/PersonalTrainerPanel/addService";

function Home() {
    const {
        isAuthenticated,
        getAccessTokenSilently,
        loginWithRedirect,
        user
      } = useAuth0();

    const [services, setServices] = useState([]);
    const [editMode, setEditMode] = useState(false);
    
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

  function extractAfterPipe(originalString) {
    const parts = originalString.split('|');
    if (parts.length === 2) {
        return parts[1]; 
    } else {
        return originalString; 
    }
}
    const { sub } = isAuthenticated ? user : {};
    const RegexUserId = sub ? extractAfterPipe(sub) : null;

    const [selectedService, setSelectedService] = useState(null);
    const [show, setShow] = useState(false);

    const handleShow = (serviceId) => {

    fetch(`${process.env.REACT_APP_BASE_URL}/api/v1/fitnessPackages/${serviceId}`, {
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
          setSelectedService(data);
          setShow(true);
      })
        .catch((error) => {
            console.error("Error fetching service details for serviceId", serviceId, ":", error);
        });
    };

    const [fitnessDataToSend, setFitnessDataToSend] = useState(null);
    const [showForm, setShowForm] = useState(false);
    
    const handleInputChange = (e) => {
      const {name, value} = e?.target || {};
      const updatedData = {
        ...fitnessDataToSend,
        [name]: value,
    };
    console.log(updatedData);
    setFitnessDataToSend(updatedData);
    };

    const handleDurationChange = (e) => {
      const { name, value } = e.target;
      const updatedData = {
        ...fitnessDataToSend,
        duration: `${value} ${name === 'duration' ? '' : value}`
      };
      console.log(updatedData);
      setFitnessDataToSend(updatedData);
    };

return (
        <div>

    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    {isAuthenticated && user["https://fitwsarah.com/roles"].length == 0 && <AddMemberProfile />}
    <section className="hero-section">
    </section>

    <section className="services-section">
      <Container>
      <h2 className="white-text">Services & Prices</h2>
      <RoleBasedSwitch user={user} role={ROLES.PERSONAL_TRAINER} onClick={() => setEditMode((prevEditMode) => !prevEditMode)}></RoleBasedSwitch>
        <Row>
          {services.map(service => (
            <Col key={service.id} md={4}>
              <div id="serviceCard" className="service-card">
                <h3>{service.title}</h3>
                <p>{service.description}</p>
                <p style={{display: 'none'}}>{service.otherInformation}</p>
                <p style={{display: 'none'}}>{service.duration}</p>
                <div className="price">{service.price}$</div>
                {!isAuthenticated && <button className="book-button" onClick={() => loginWithRedirect({authorizationParams: { screen_hint: "login"}})}>Book</button>}
                {isAuthenticated && <Link to={`/bookAppointments/?serviceId=${service.serviceId}&userId=${RegexUserId}`}><button className="book-button">Book</button></Link>}
                <button className="book-button" onClick={() => handleShow(service.serviceId)}>Details</button>
              </div>
            </Col>
          ))}
        </Row>
        
        {editMode && <button onClick={<RequestNewFitnessService />} className="my-button">Click me</button>}
      
      </Container>
    </section>

    <Modal show={show} onHide={() => setShow(false)}>
    <Modal.Header closeButton>
        <Modal.Title>{selectedService?.title}</Modal.Title>
    </Modal.Header>
    <Modal.Body>{selectedService?.description}</Modal.Body>
    <Modal.Body>{selectedService?.otherInformation}</Modal.Body>
    <Modal.Body>{selectedService?.duration}</Modal.Body>
    <Modal.Body><p>{selectedService?.price}$</p></Modal.Body>
    {isAuthenticated && <Modal.Footer style={{textAlign: 'right'}}>
    <button className="book-button">Book</button>
    </Modal.Footer>}
    </Modal>
    
    <Modal show={showForm} onHide={()=>setShowForm(false)}>
        <Modal.Header closeButton>
          <Modal.Title>Add a Fitness Service</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <form>
          <div className="form-group">
            <input type="text" id="fitnessServiceName" maxLength="50" placeholder="Fitness Service Title" name="fitnessServiceName" required  onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
          <input type="number" id="duration" maxLength="2" placeholder="Duration" name="duration" required  onChange={(e) => handleDurationChange(e)} />
            <select id="durationType" name="durationType"  onChange={(e) => handleDurationChange(e)}  required>
                <option value="minutes">minutes</option>
                <option value="hour(s)">hour(s)</option>
            </select>
          </div>
          <div className="form-group">
            <input type="number" id="price"  placeholder="Price" name="price" required onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
            <input type="textarea" id="description"  placeholder="Description" name="description" required onChange={(e) => handleInputChange(e)} />
          </div>
          <div className="form-group">
            <input type="textarea" id="other"  placeholder="Other Information (Optional)" name="other"  onChange={(e) => handleInputChange(e)} />
          </div>
          
          <AddServiceButton fitnessDataToSend={fitnessDataToSend}></AddServiceButton>

        </form>
        </Modal.Body>

    </Modal>



    <FooterNotLoggedIn/>
  </div>
    );
}

export default Home;
