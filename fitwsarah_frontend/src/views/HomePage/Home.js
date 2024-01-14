import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Row, Col, Modal } from 'react-bootstrap';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import AddMemberProfile from "../authentication/Signup";


function Home() {
    const {
        isAuthenticated,
        getAccessTokenSilently,
        loginWithRedirect,
      } = useAuth0();

    const [services, setServices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);
    const [hasCalledAddMemberProfile, setHasCalledAddMemberProfile] = useState(false);
   // const history = useHistory();  
    
    useEffect(() => {
      getAllFitnessServices();
    }, []);

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
          // Calling the addMember only once
          setHasCalledAddMemberProfile(true);
      }
  }, [isAuthenticated, hasCalledAddMemberProfile]);

    const handleNewAppointment = (serviceId) => {
     // history.push('/appointments?serviceId=${serviceId}')
    }
  
//Calls the AddMember function only once
    return (
        <div>

    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    {!hasCalledAddMemberProfile && isAuthenticated && <AddMemberProfile />}
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
                <button className="book-button" onClick={() => isAuthenticated ? handleNewAppointment(service.serviceId) : loginWithRedirect({authorizationParams: { screen_hint: "signup"}})}>Book</button>
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
