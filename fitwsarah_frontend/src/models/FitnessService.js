import axios from "axios";
import FitnessServiceCard from "../components/home/FitnessServiceCard";
import { useState, useEffect } from "react";
import { Row, Col } from "react-bootstrap";

function FintessServiceList() {
    const [FinessServices, setFitnessServices] = useState([]);

    useEffect(() => {
        getAllFitnessServices();
    }, []);

    const getAllFitnessServices = () => {
        axios.get("http://localhost:8080/api/v1/fitnessPackages")
        .then((response) => {
            console.log(response);
            setFitnessServices(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    }

    return (
        <div>
            <Row xs={1} md={2} lg={3} className="g-4">
            {FinessServices.map((service) => (
                 <Col key={service.id}>
                <FitnessServiceCard 
                    key={service.serviceId} 
                    fitnessService={{
                        serviceId: service.serviceId,
                        promoId: service.promoId,
                        title: service.title,
                        duration: service.duration,
                        description: service.description,
                        price: service.price
                    }}/>
                    </Col>
            ))}
            </Row>
        </div>
    );
}

export default FintessServiceList;