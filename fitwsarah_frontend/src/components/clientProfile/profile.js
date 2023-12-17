import React from 'react';
import "../../css/style.css";
import { Container, Row, Col, Button } from 'react-bootstrap';
import Profile from '../../views/ProfilePage/Account';

function ProfileSideBar({ username, email, city }) {
    return (
        <section className="appointments-section">
            <Container>
                <h2 style={{ color: 'white' }}></h2>
                <Row>
                    <Col md={12}>
                        <div className="appointment-card">
                            <p>
                                <p> Upcoming Appointment </p>

                                <Button variant="primary" className="float-right">Scheduled</Button>
                            </p>
                        </div>
                    </Col>
                </Row>
            </Container>
        </section>
    );
}

export default ProfileSideBar;
