import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { ROLES } from "../../components/authentication/roles";
import "../../components/authentication/switch.css"
import RoleBasedSwitch from "../../components/authentication/RoleBasedSwitch";
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReactStars from 'react-stars'
import AddFeedbackButton from '../../components/feedback/newFeedbackBtn';
import "../ContactMePage/contact.css";
function Home() {
    const {
        isAuthenticated,
        getAccessTokenSilently,
        loginWithRedirect,
        user
      } = useAuth0();

    const [services, setServices] = useState([]);
    const [editMode, setEditMode] = useState(false);

  function extractAfterPipe(originalString) {
    const parts = originalString.split('|');
    if (parts.length === 2) {
        return parts[1]; 
    } else {
        return originalString; 
    }
}
    const { sub } = isAuthenticated ? user : {};
    const RegexUserId = sub ? extractAfterPipe(sub) : null;

    const [selectedService, setSelectedService] = useState(null);
    const [show, setShow] = useState(false);
    const [feedbackDataToSend, setFeedbackDataToSend] = useState({
        userId:RegexUserId
    });
    
    const handleInputChange = (e) => {
      const {name, value} = e?.target || {};
      const updatedData = {
        ...feedbackDataToSend,
        [name]: value,
    };
    setFeedbackDataToSend(updatedData);
    };

    const handleStarsChange = (e) => {
        const {value} = e?.target || {};
        const updatedData = {
          ...feedbackDataToSend,
          stars: value,
      };
      setFeedbackDataToSend(updatedData);
      };

return (
<div>
    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    <div id="contactBackground">
        <div className="header-contain"> 
            <h1 className="white-title">Contact Me</h1>      
        </div> 
        <RoleBasedSwitch user={user} role={ROLES.PERSONAL_TRAINER} onClick={() => setEditMode((prevEditMode) => !prevEditMode)}></RoleBasedSwitch>
        <div className="feedback-container">
            <div className="sidebar">
            <aside>
                <h4 className="contact-title">My Contact Information</h4>
                <p className="contact-text">Phone Number: +189 789 667</p>
                <p className="contact-text">Email Address: sarah.fitness@gmail.com</p>
            </aside>
            </div>
            <div className="account-container">
                <h3>Provide your Feedback!</h3>
                <ReactStars count={5} half={false} onChange={(e) => handleStarsChange(e)} size={30} color2={'#ffd700'} />
                <div className="form-group">
                    <label htmlFor="content">Content</label>
                    <textarea id="content" placeholder="Content here" name="content" required onChange={(e) => handleInputChange(e)} />
                </div>
                <AddFeedbackButton feedbackDataToSend={feedbackDataToSend}/>
            </div>
        </div>

    

    <FooterNotLoggedIn/>
  </div>
</div>
    );
}

export default Home;
