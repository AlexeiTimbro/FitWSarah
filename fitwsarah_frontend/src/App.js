import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import FitnessServiceList from './views/HomePage/FitnessService';
import Profile from './views/ProfilePage/Account';
import Settings from './components/clientProfile/setting';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<FitnessServiceList />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/settings" element={<Settings />} />
            </Routes>
        </Router>
    );
}

export default App;
