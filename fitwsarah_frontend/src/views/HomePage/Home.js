import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Row, Col, Modal } from 'react-bootstrap';
import "../../css/style.css";
import { Link } from 'react-router-dom';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import AddMemberProfile from "../authentication/Signup";


function Home() {
    const {
        isAuthenticated,
        getAccessTokenSilently,
        loginWithRedirect,
        user
      } = useAuth0();

    const [services, setServices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const [hasCalledAddMemberProfile, setHasCalledAddMemberProfile] = useState(false);
    
    useEffect(() => {
      getAllFitnessServices();
    }, []);

    useEffect(() => {
      if (isAuthenticated && !hasCalledAddMemberProfile) {
          setHasCalledAddMemberProfile(true);
      }
  }, [isAuthenticated, hasCalledAddMemberProfile]);

    const getAllFitnessServices = () => {
      fetch("http://localhost:8080/api/v1/fitnessPackages", {
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

    fetch(`http://localhost:8080/api/v1/fitnessPackages/${serviceId}`, {
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
    
    useEffect(() => {
      if (isAuthenticated && !hasCalledAddMemberProfile) {
          setHasCalledAddMemberProfile(true);
      }
  }, [isAuthenticated, hasCalledAddMemberProfile]);
   
return (
        <div>

    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    {isAuthenticated && <AddMemberProfile />}
    <section className="hero-section">
    </section>

    <section className="services-section">
      <Container>
      <h2 className="white-text">Services & Prices</h2>
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
    <FooterNotLoggedIn/>
  </div>
    );
}

export default Home;
