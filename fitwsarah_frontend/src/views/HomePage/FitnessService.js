import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import { Container, Row, Col, Modal, Button } from 'react-bootstrap';
import "../../css/style.css";
import LoginButton from "../../components/authentication/login";
import LogoutButton from "../../components/authentication/logout";
import axios from 'axios'; 
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import AddMemberProfile from "../authentication/Signup";


function FitnessServiceList() {

    const {
        isAuthenticated,
        getAccessTokenSilently,
      } = useAuth0();

    const [services, setServices] = useState([]);
    const [accessToken, setAccessToken] = useState(null);

    useEffect(() => {
        if (isAuthenticated) {
          const getAccessToken = async () => {
            try {
              const token = await getAccessTokenSilently({
                audience: configData.audience,
                scope: configData.scope,
              });
              setAccessToken(token);
            } catch (e) {
              console.error(e.message);
            }
          };
          getAccessToken();
        }
      }, [getAccessTokenSilently, isAuthenticated]);

    useEffect(() => {
        if (accessToken) {
            getAllFitnessServices();
        }
    }, [accessToken]);


    const getAllFitnessServices = () => {
      fetch("http://localhost:8080/api/v1/fitnessPackages", {
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
          setSelectedService(data);
          setShow(true);
      })
        .catch((error) => {
            console.error("Error fetching service details for serviceId", serviceId, ":", error);
        });
    };
  

    return (
        <div>

    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    {isAuthenticated && <AddMemberProfile/>}
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
                <button className="book-button">Book</button>
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
    <Modal.Footer style={{textAlign: 'right'}}>
    <button className="book-button">Book</button>
    </Modal.Footer>
    </Modal>

    <FooterNotLoggedIn/>
  </div>
    );
}

export default FitnessServiceList;
