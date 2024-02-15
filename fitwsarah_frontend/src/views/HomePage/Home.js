import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import { Container, Row, Col, Modal, Form, Button } from 'react-bootstrap';

import "../../css/style.css";
import { Link } from 'react-router-dom';
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footer";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import AddMemberProfile from "../authentication/Signup";
import { ROLES } from "../../components/authentication/roles";
import "../../components/authentication/switch.css"
import RoleBasedSwitch from "../../components/authentication/RoleBasedSwitch";
import AddServiceButton from "../../components/PersonalTrainerPanel/addService";
import 'react-toastify/dist/ReactToastify.css';
import { useTranslation } from "react-i18next";
import { useLanguage } from '../../LanguageConfig/LanguageContext';
import { useGetAccessToken } from "../../components/authentication/authUtils";
import "./Editbutton.css";


function Home() {
    const {
        isAuthenticated,
        loginWithRedirect,
        user
    } = useAuth0();

    const [services, setServices] = useState([]);
    const [editMode, setEditMode] = useState(false);
    const [accessToken, setAccessToken] = useState(null);
    const getAccessToken = useGetAccessToken();
    const [packageVisibility, setPackageVisibility] = useState({}); // State to manage visibility of fitness packages
   const[showVisible, setShowVisible] = useState('VISIBLE');

    const handleVisibilityChange = (serviceId, status) => {
        if (status === 'VISIBLE') {
            publishConfirmation(serviceId);
        } else if (status === 'INVISIBLE') {
            hideFitnessPackage(serviceId);
            unpublishConfirmation(serviceId);
        }
    };

    const hideFitnessPackage = (serviceId) => {
        setPackageVisibility(prevState => ({
            ...prevState,
            [serviceId]: false
        }));
    };

    const {t} = useTranslation('home');
    const {language} = useLanguage();

    useEffect(() => {
        const fetchToken = async () => {
          const token = await getAccessToken();
          if (token) setAccessToken(token);
        };

        fetchToken();
      }, [getAccessToken]);


    useEffect(() => {
        getAllFitnessServices();
    }, []);

    const getAllFitnessServices = () => {

        const params = new URLSearchParams();


        fetch(`http://localhost:8080/api/v1/fitnessPackages${params.toString() && "?" + params.toString()}`, {
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

    const updateFitnessServiceByStatus = async (serviceId, status) => {

        fetch(`http://localhost:8080/api/v1/fitnessPackages/${serviceId}/invisible`, {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${accessToken}`,
                "Content-Type": "application/json"
            },
            body: status
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok ' + response.statusText);
                }
                return response.json();
            })
            .then((data) => {
                console.log(data)
                getAllFitnessServices();
            })
            .catch((error) => {
                console.log(error);
            });
    }


    function extractAfterPipe(originalString) {
        const parts = originalString.split('|');
        if (parts.length === 2) {
            return parts[1];
        } else {
            return originalString;
        }
    }

    const {sub} = isAuthenticated ? user : {};
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


    const handleUpdateService = (serviceId) => {
                fetch(`http://localhost:8080/api/v1/fitnessPackages/${serviceId}`, {
                    method: "PUT",
                    headers: {
                        Authorization: `Bearer ${accessToken}`,
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(selectedService)
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error(`HTTP error! Status: ${response.status}`);
                        }
                        return response.json();
                    })
                    .then(updatedService => {
                        console.log("Service updated successfully", updatedService);
                        getAllFitnessServices();
                        setShowUpdateForm(false);
                    })
                    .catch(error => {
                        console.error("Error updating service:", error);
                    });
    };



    const publishConfirmation  = (serviceId) => {
        const answer = window.confirm(t("areyousure_publish"));
        if(answer){
            updateFitnessServiceByStatus(serviceId, 'VISIBLE');
        }
    }

    const unpublishConfirmation  = (serviceId) => {
        const answer = window.confirm(t('areyousure_UNpublish'));
        if(answer){
            updateFitnessServiceByStatus(serviceId, 'INVISIBLE');
        }
    }
    const handleEdit = (service) => {
        setSelectedService(service);
        setShowUpdateForm(true);
    };

    const [fitnessDataToSend, setFitnessDataToSend] = useState({});
    const [durationType, setDurationType] = useState('minutes');
    const [showForm, setShowForm] = useState(false);
    const [showUpdateForm, setShowUpdateForm] = useState(false);

    const handleInputChange = (e) => {
        const {name, value} = e?.target || {};
        const updatedData = {
            ...fitnessDataToSend,
            [name]: value,
        };
        setFitnessDataToSend(updatedData);
    };


    const handleChange = (e) => {
        const {name, value} = e.target;
        setSelectedService(prevState => ({
            ...prevState,
            [name]: value
        }));
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
      const {value } = e.target;
      const updatedData = {
        ...fitnessDataToSend,
        duration: `${value} ${durationType}`
      };
      console.log(updatedData)
      setFitnessDataToSend(updatedData);
    };

    return (
        <div>
            {!isAuthenticated && <NavNotLoggedIn/>}
            {isAuthenticated && <NavLoggedIn/>}
            {isAuthenticated && user["https://fitwsarah.com/roles"].length === 0 && <AddMemberProfile/>}

            <section className="hero-section">
            </section>

            <section className="services-section">
                <Container>
                    <RoleBasedSwitch user={user} role={ROLES.PERSONAL_TRAINER}
                                     onClick={() => setEditMode(prevEditMode => !prevEditMode)}/>
                    <div className="header-container">
                        <h2 className="white-text">{t('servicesAndPrices')}</h2>
                    </div>

                    <Row>
                        {services.map(service => (
                            <Col key={service.id} md={4}>
                                {showVisible === 'VISIBLE' && service.status === 'VISIBLE' && (
                                    <div id="serviceCard" className="service-card">
                                        <h3>{language === 'en' ? service.title_EN : service.title_FR}</h3>
                                        <p>{language === 'en' ? service.description_EN : service.description_FR}</p>
                                        <p style={{ display: 'none' }}>{language === 'en' ? service.otherInformation_EN : service.otherInformation_FR}</p>
                                        <p style={{ display: 'none' }}>{service.duration}</p>
                                        <div className="price">{service.price} $</div>

                                        {!isAuthenticated && <button className="book-button"
                                                                     onClick={() => loginWithRedirect({authorizationParams: {screen_hint: "login"}})}>{t('book')}</button>}
                                        {isAuthenticated && <Link
                                            to={`/bookAppointments/?serviceId=${service.serviceId}&userId=${RegexUserId}`}>
                                            <button className="book-button">{t('book')}</button>
                                        </Link>}
                                        <button className="book-button"
                                                onClick={() => handleShow(service.serviceId)}>{t('details')}</button>
                                        {editMode &&
                                            <button onClick={() => handleEdit(service)} className="edit-button">
                                                <svg width="24" height="24" viewBox="0 0 24 24" fill="none"
                                                     xmlns="http://www.w3.org/2000/svg">
                                                    <path
                                                        d="M3 17.25V21H6.75L17.81 9.94L14.06 6.19L3 17.25ZM20.71 7.04C21.1 6.65 21.1 6.02 20.71 5.63L18.37 3.29C17.98 2.9 17.35 2.9 16.96 3.29L15.13 5.12L18.88 8.87L20.71 7.04Z"
                                                        fill="black"/>
                                                </svg>
                                            </button>
                                        }

                                    </div>
                                )}
                                {editMode && (
                                    <div>
                                        {service.status === "INVISIBLE" && (
                                            <button className="button details-button" onClick={() => publishConfirmation(service.serviceId)}>
                                                <svg
                                                    width="24"
                                                    height="24"
                                                    viewBox="0 0 24 24"
                                                    fill="none"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                >
                                                    <path
                                                        fillRule="evenodd"
                                                        clipRule="evenodd"
                                                        d="M4 12C4 11.4477 4.44772 11 5 11H19C19.5523 11 20 11.4477 20 12C20 12.5523 19.5523 13 19 13H5C4.44772 13 4 12.5523 4 12ZM5 9H19C19.5523 9 20 8.55228 20 8C20 7.44772 19.5523 7 19 7H5C4.44772 7 4 7.44772 4 8C4 8.55228 4.44772 9 5 9ZM15 15H5C4.44772 15 4 15.4477 4 16C4 16.5523 4.44772 17 5 17H15C15.5523 17 16 16.5523 16 16C16 15.4477 15.5523 15 15 15Z"
                                                        fill="black"
                                                    />
                                                </svg>
                                            </button>
                                        )}
                                        {service.status === "VISIBLE" && (
                                            <button className="button details-button" onClick={() => unpublishConfirmation(service.serviceId)}>
                                                <svg
                                                    width="24"
                                                    height="24"
                                                    viewBox="0 0 24 24"
                                                    fill="none"
                                                    xmlns="http://www.w3.org/2000/svg"
                                                >
                                                    <path
                                                        fillRule="evenodd"
                                                        clipRule="evenodd"
                                                        d="M8 5C8 4.44772 8.44772 4 9 4H15C15.5523 4 16 4.44772 16 5V19C16 19.5523 15.5523 20 15 20H9C8.44772 20 8 19.5523 8 19V5ZM9 2H15C16.6569 2 18 3.34315 18 5V19C18 20.6569 16.6569 22 15 22H9C7.34315 22 6 20.6569 6 19V5C6 3.34315 7.34315 2 9 2ZM14 10V14H10V10H14Z"
                                                        fill="black"
                                                    />
                                                </svg>
                                            </button>
                                        )}
                                    </div>
                                )}

                            </Col>
                        ))}
                    </Row>

                    {editMode && (
                        <button onClick={() => setShowForm(prevShowForm => !prevShowForm)} className="add-button">
                            <xml version="1.0" encoding="utf-8"/>
                            <svg width="40px" height="40px" viewBox="0 0 24 24" fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M4 12H20M12 4V20" stroke="#000000" stroke-width="2" stroke-linecap="round"
                                      stroke-linejoin="round"/>
                            </svg>
                        </button>
                    )}
                </Container>
            </section>

            <Modal show={show} onHide={() => setShow(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{language === 'en' ? selectedService?.title_EN : selectedService?.title_FR}</Modal.Title>
                </Modal.Header>
                <Modal.Body>{language === 'en' ? selectedService?.description_EN : selectedService?.description_FR}</Modal.Body>
                <Modal.Body>{language === 'en' ? selectedService?.otherInformation_EN : selectedService?.otherInformation_FR}</Modal.Body>
                <Modal.Body>{selectedService?.duration}</Modal.Body>
                <Modal.Body><p>{selectedService?.price}$</p></Modal.Body>
                {isAuthenticated && (
                    <Modal.Footer style={{textAlign: 'right'}}>
                        <button className="book-button">{t('book')}</button>
                    </Modal.Footer>
                )}
            </Modal>

            <Modal show={showForm} onHide={() => setShowForm(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('addFitnessService')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form>
                        <div className="form-group">
                            <input type="text" id="title_EN" maxLength="50" placeholder={t('fitnessServiceTitle_en')}
                                   name="title_EN" required onChange={(e) => handleInputChange(e)}/>
                            <input type="text" id="title_FR" maxLength="50" placeholder={t('fitnessServiceTitle_fr')}
                                   name="title_FR" required onChange={(e) => handleInputChange(e)}/>
                        </div>

                        <div className="form-group">
                            <input type="number" id="duration" max="99" placeholder={t('duration')} name="duration"
                                   required onChange={(e) => handleDurationChange(e)}/>
                            <select id="durationType" name="durationType"
                                    onChange={(e) => setDurationType(e.target.value)} required>
                                <option value="minutes">{t('minutes')}</option>
                                <option value="hour(s)">{t('hours')}</option>
                            </select>
                        </div>
                        <div className="form-group">
                            <input type="number" id="price" placeholder={t('price')} name="price" required
                                   onChange={(e) => handlePriceChange(e)}/>
                        </div>
                        <div className="form-group">
                            <textarea id="description_EN" placeholder={t('description_en')} name="description_EN"
                                      required onChange={(e) => handleInputChange(e)}/>
                            <textarea id="description_FR" placeholder={t('description_fr')} name="description_FR"
                                      required onChange={(e) => handleInputChange(e)}/>
                        </div>
                        <div className="form-group">
                            <textarea id="otherInformation_EN" placeholder={t('otherInformationOptional_en')}
                                      name="otherInformation_EN" onChange={(e) => handleInputChange(e)}/>
                            <textarea id="otherInformation_FR" placeholder={t('otherInformationOptional_fr')}
                                      name="otherInformation_FR" onChange={(e) => handleInputChange(e)}/>
                        </div>
                    </form>
                    <AddServiceButton getAllFitnessServices={getAllFitnessServices} fitnessDataToSend={fitnessDataToSend} setShowForm={setShowForm}/>
                </Modal.Body>
            </Modal>

            <Modal show={showUpdateForm} onHide={() => setShowUpdateForm(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>{t('updateFitnessService')}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>

                        <Form.Group className="mb-3">
                            <Form.Label>{t('fitnessServiceTitle_en')}</Form.Label>
                            <Form.Control
                                type="text"
                                name="title_EN"
                                value={selectedService?.title_EN || ''}
                                onChange={handleChange}
                            />
                            <Form.Label>{t('fitnessServiceTitle_fr')}</Form.Label>
                            <Form.Control
                                type="text"
                                name="title_FR"
                                value={selectedService?.title_FR || ''}
                                onChange={handleChange}
                            />
                        </Form.Group>


                        <Form.Group className="mb-3">
                            <Form.Label>{t('description_en')}</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="description_EN"
                                value={selectedService?.description_EN || ''}
                                onChange={handleChange}
                            />
                            <Form.Label>{t('description_fr')}</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="description_FR"
                                value={selectedService?.description_FR || ''}
                                onChange={handleChange}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>{t('otherInformationOptional_en')}</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="otherInformation_EN"
                                value={selectedService?.otherInformation_EN || ''}
                                onChange={handleChange}
                            />
                            <Form.Label>{t('otherInformationOptional_fr')}</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="otherInformation_FR"
                                value={selectedService?.otherInformation_FR || ''}
                                onChange={handleChange}
                            />
                        </Form.Group>


                        <Form.Group className="mb-3">
                            <Form.Label>{t('price')}</Form.Label>
                            <Form.Control
                                type="number"
                                name="price"
                                value={selectedService?.price || ''}
                                onChange={handleChange}
                            />
                        </Form.Group>

                        <Form.Group className="mb-3">
                            <Form.Label>Duration</Form.Label>
                            <Form.Control
                                type="text"
                                name="duration"
                                value={selectedService?.duration || ''}
                                onChange={handleChange}
                            />
                        </Form.Group>


                        <Button variant="primary" onClick={() => {
                            handleUpdateService(selectedService.serviceId)

                        }}>
                            Update Service
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>


            <FooterNotLoggedIn/>
        </div>
    );

}
export  default Home;