import React from 'react';
import "../../css/style.css";
import { Container, Row, Col } from 'react-bootstrap';


function ProfileSideBar() {
    return (
        <section className="appointments-section">
            <Container>
                <h2 style={{ color: 'white' }}>Upcomming Appointments</h2>
                <Row>
                    <Col md={12}>
                        <div className="appointment-card">
                            <p>
                                <p>Appointment Date: YYYY-MM-DD</p>
                                <p>Appointment Time: HH:MM AM/PM</p>
                            </p>
                            <p>
                                <p>Appointment Date: YYYY-MM-DD</p>
                                <p>Appointment Time: HH:MM AM/PM</p>
                            </p>
                        </div>
                    </Col>
                </Row>
            </Container>
        </section>
    );
}

export default ProfileSideBar;