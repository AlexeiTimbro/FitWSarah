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
import AdminInvoices from "./views/AdminPanelPage/AdminInvoices";
import CreateAdminInvoices from "./views/AdminPanelPage/AdminCreateInvoice";
import TrainerCreateInvoices from "./views/PersonalTrainerPanel/TrainerCreateInvoice";
import BookAppointment from "./views/AppointmentPage/NewAppointment";
import Services from "./views/AdminPanelPage/FitnessServices";
import TrainerFitnessServices from "./views/PersonalTrainerPanel/TrainerFitnessServices";
import AdminCoachNote from './views/AdminPanelPage/AdminCoachNotes';
import TrainerInvoices from "./views/PersonalTrainerPanel/TrainerInvoices";
import ContactMe from "./views/ContactMePage/ContactMeView";
import Feedbacks from "./views/PersonalTrainerPanel/TrainerFeedback";
import AdminFeedback from "./views/AdminPanelPage/Feedback";
import { LanguageProvider } from './LanguageConfig/LanguageContext.js';
import ClientInvoices from "./components/clientProfile/clientInvoices";
import TrainerCoachNotes from "./views/PersonalTrainerPanel/TrainerCoachNotes";
import AdminCreateCoachNote from "./views/AdminPanelPage/AdminCreateCoachNotes";
import TrainerCreateCoachNotes from "./views/PersonalTrainerPanel/TrainerCreateCoachNote";

function App() {
    return (
        <LanguageProvider>
            <Router>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/profile" element={<Profile/>}/>
                    <Route path="/bookAppointments" element={<BookAppointment/>}/>
                    <Route path="/settings" element={<Settings/>}/>
                    <Route path="/adminPanel" element={<AdminPanel/>}/>
                    <Route path="/adminAccounts" element={<AdminAccounts/>}/>
                    <Route path="/adminAppointments" element={<AdminAppointments/>}/>
                    <Route path="/trainerPanel" element={<TrainerPanel/>}/>
                    <Route path="/trainerAppointments" element={<TrainerAppointments/>}/>
                    <Route path="/trainerAccounts" element={<TrainerAccounts/>}/>
                    <Route path="/services" element={<Services/>}/>
                    <Route path="/trainerServices" element={<TrainerFitnessServices/>}/>
                    <Route path="/adminInvoices" element={<AdminInvoices/>}/>
                    <Route path="/CreateAdminInvoices" element={<CreateAdminInvoices/>}/>
                    <Route path="/trainerInvoices" element={<TrainerInvoices/>}/>
                    <Route path="/trainerCreateInvoices" element={<TrainerCreateInvoices/>}/>
                    <Route path="/contactMe" element={<ContactMe/>}/>
                    <Route path="/trainerFeedback" element={<Feedbacks/>}/>
                    <Route path="/adminFeedback" element={<AdminFeedback/>}/>
                    <Route path="/invoices" element={<ClientInvoices/>}/>
                    <Route path="/adminCoachNotes" element={<AdminCoachNote/>}/>
                    <Route path="/trainerCoachNotes" element={<TrainerCoachNotes/>}/>
                    <Route path="/AdminCreateCoachNotes" element={<AdminCreateCoachNote/>}/>
                    <Route path="/TrainerCreateCoachNotes" element={<TrainerCreateCoachNotes/>}/>

                </Routes>
            </Router>
        </LanguageProvider>
    );


}
export default App;
