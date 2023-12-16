import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import FitnessServiceList from './views/HomePage/FitnessService';
import Profile from './views/ProfilePage/Account';
import Settings from './components/clientProfile/setting';
import AdminPanel from "./views/AdminPanelPage/AdminPanel";
import AdminAccounts from "./views/AdminPanelPage/AdminAccounts";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<FitnessServiceList/>}/>
                <Route path="/profile" element={<Profile/>}/>
                <Route path="/settings" element={<Settings/>}/>
                <Route path="/" element={<FitnessServiceList/>}/>
                <Route path="/adminPanel" element={<AdminPanel/>}/>
                <Route path="/adminAccounts" element={<AdminAccounts/>}/>
            </Routes>
        </Router>
    );

}
export default App;
