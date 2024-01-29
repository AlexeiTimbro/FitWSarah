import React, { useEffect, useState } from 'react';
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Form, Button } from 'react-bootstrap';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import configData from "../../config.json";

function UpdateFitnessPackage() {
    const { isAuthenticated, getAccessTokenSilently, user } = useAuth0();
    const [accessToken, setAccessToken] = useState(null);
    const [fitnessPackage, setFitnessPackage] = useState({
        title: '',
        duration: '',
        price: '',
        description: '',
        otherInformation: ''
    });

    useEffect(() => {
        if (isAuthenticated) {
            const getAccessToken = async () => {
                try {
                    const token = await getAccessTokenSilently({
                        audience: process.env.REACT_APP_AUTH0_AUDIENCE,
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

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFitnessPackage(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleUpdate = (serviceId) => {
         // Replace with actual service ID
        fetch(`http://localhost:8080/api/v1/fitnessPackages/${serviceId}`, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + accessToken,
                "Content-Type": "application/json",
            },
            body: JSON.stringify(fitnessPackage),
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                console.log("Fitness package updated successfully", data);
                // Handle successful update (e.g., show confirmation, refresh data)
            })
            .catch((error) => {
                console.error("Error updating fitness package:", error);
            });
    };

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            <Container>
                <h1>Update Fitness Package</h1>
                <Form onSubmit={handleUpdate}>
                    <Form.Group controlId="formTitle">
                        <Form.Label>Title</Form.Label>
                        <Form.Control
                            type="text"
                            name="title"
                            value={fitnessPackage.title}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formTotalDuration">
                        <Form.Label>Total Duration</Form.Label>
                        <Form.Control
                            type="text"
                            name="totalDuration"
                            value={fitnessPackage.totalDuration}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formDescription">
                        <Form.Label>Description</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="description"
                            value={fitnessPackage.description}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Form.Group controlId="formOtherInformation">
                        <Form.Label>Other Information</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="otherInformation"
                            value={fitnessPackage.otherInformation}
                            onChange={handleInputChange}
                        />
                    </Form.Group>

                    <Button
                        variant="primary"
                        type="submit"
                        style={{ marginTop: '20px' }}
                    >
                        Update Package
                    </Button>
                </Form>
            </Container>

            <FooterNotLoggedIn/>
        </div>
    );
}



export default UpdateFitnessPackage;
