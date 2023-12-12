import { Card, Button } from 'react-bootstrap';

function FitnessServiceCard({ fitnessService }) {
    return (
        <Card style={{ width: '18rem', margin: 'auto', textAlign: 'center', backgroundColor: '#343a40', color: 'white' }}>
            <Card.Body>
                <Card.Title style={{ color: '#fff' }}>{fitnessService.title}</Card.Title>
                <Card.Text>
                    {fitnessService.description}
                </Card.Text>
                <Card.Text>
                    {fitnessService.price}
                </Card.Text>
                <Button variant="light">Book</Button>
            </Card.Body>
        </Card>
    );
}

export default FitnessServiceCard;
