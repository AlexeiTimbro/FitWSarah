import axios from "axios";
import FitnessServiceCard from "../components/home/FitnessServiceCard";
import { useState, useEffect } from "react";
import { Container, Row, Col } from "react-bootstrap";

function FitnessServiceList() {
    const [services, setServices] = useState([]);

    useEffect(() => {
        getAllFitnessServices();
    }, []);

    const getAllFitnessServices = () => {
        axios.get("http://localhost:8080/api/v1/fitnessPackages")
        .then((response) => {
            setServices(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
    }

    return (
        <Container>
            <h2 style={{ textAlign: 'center', margin: '2rem 0' }}>Services & Prices</h2>
            <Row xs={1} md={3} className="g-4">
                {services.map((service) => (
                    <Col key={service.serviceId}>
                        <FitnessServiceCard fitnessService={service} />
                    </Col>
                ))}
            </Row>
        </Container>
    );
}

export default FitnessServiceList;
