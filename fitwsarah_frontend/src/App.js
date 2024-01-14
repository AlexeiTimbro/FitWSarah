import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './views/HomePage/Home';
import Profile from './views/ProfilePage/Account';
import Settings from './components/clientProfile/setting';
import AdminPanel from "./views/AdminPanelPage/AdminPanel";
import AdminAccounts from "./views/AdminPanelPage/AdminAccounts";
import TrainerAppointments from "./views/PersonalTrainerPanel/TrainerAppointments";
import TrainerAccounts from "./views/PersonalTrainerPanel/TrainerAccounts";
import TrainerPanel from "./views/PersonalTrainerPanel/TrainerPanel";
import AdminAppointments from "./views/AdminPanelPage/AdminAppointments";
import BookAppointment from "./views/AppointmentPage/NewAppointment";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/profile" element={<Profile/>}/>
                <Route path="/newAppointment" element={<BookAppointment/>}/>
                <Route path="/settings" element={<Settings/>}/>
                <Route path="/adminPanel" element={<AdminPanel/>}/>
                <Route path="/adminAccounts" element={<AdminAccounts/>}/>
                <Route path="/adminAppointments" element={<AdminAppointments/>}/>
                <Route path="/trainerPanel" element={<TrainerPanel/>}/>
                <Route path="/trainerAppointments" element={<TrainerAppointments/>}/>
                <Route path="/trainerAccounts" element={<TrainerAccounts/>}/>
            </Routes>
        </Router>
    );

}
export default App;
