import { useState, useEffect } from "react";
import { Container, Row, Col } from "react-bootstrap";
import { useAuth0 } from '@auth0/auth0-react';
import configData from '../../config.json'
import "../../css/style.css";
import LoginButton from "../../components/authentication/login";
import LogoutButton from "../../components/authentication/logout";

import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";


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

    useEffect(() => {
        getFitnessServiceByServiceId();
    }, []);

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
    }

    const getFitnessServiceByServiceId = () => {
        axios.get("http://localhost:8080/api/v1/fitnessPackages/{serviceId}")
        .then((response) => {
            setServices(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
    }

    return (
        <div className="main-page">
        <header className="main-header">
            <div className="logo">FITWSARAH</div>
            <nav className="main-nav">
                <a href="/about">About</a>
                {!isAuthenticated && <LoginButton/>}
                {isAuthenticated && <LogoutButton/>}
            </nav>
        </header>


        <NavNotLoggedIn/>
        <section className="hero-section">
        </section>

        <section className="services-section">
            <Container>
                <h2>Services & Prices</h2>
                <Row>
                    {services.map(service => (
                        <Col key={service.id} md={4}>
                            <div id="serviceCard" className="service-card">
                                <h3>{service.title}</h3>
                                <p>{service.description}</p>
                                <div className="price">{service.price}$</div>
                                <button className="book-button">Book</button>
                                <button className="book-button">Details</button>
                            </div>
                        </Col>
                    ))}
                </Row>
            </Container>
        </section>
        <FooterNotLoggedIn/>
    </div>
        

    );
}

export default FitnessServiceList;
