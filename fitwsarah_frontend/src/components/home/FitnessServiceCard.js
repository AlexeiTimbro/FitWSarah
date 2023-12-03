import { Card, Button } from 'react-bootstrap';

function FitnessServiceCard({ fitnessService }) {
    return (
        <Card style={{ width: '18rem' }}> {/* Adjust width as needed */}
            <Card.Body>
                <Card.Title>{fitnessService.title}</Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                    Duration: {fitnessService.duration}
                </Card.Subtitle>
                <Card.Text>
                    {fitnessService.description}
                </Card.Text>
                <Card.Text>
                    Price: {fitnessService.price}
                </Card.Text>
                <Button variant="primary">Book</Button>
            </Card.Body>
        </Card>
    );
}

export default FitnessServiceCard;
