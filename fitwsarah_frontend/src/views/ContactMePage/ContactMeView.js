import { useState, useEffect } from "react";
import { useAuth0 } from '@auth0/auth0-react';
import "../../css/style.css";
import NavNotLoggedIn from "../../components/navigation/NotLoggedIn/navNotLoggedIn";
import FooterNotLoggedIn from "../../components/footer/footerNotLoggedIn/footerNotLoggedIn";
import NavLoggedIn from "../../components/navigation/loggedIn/navLoggedIn";
import { ROLES } from "../../components/authentication/roles";
import "../../components/authentication/switch.css"
import RoleBasedSwitch from "../../components/authentication/RoleBasedSwitch";
import 'react-toastify/dist/ReactToastify.css';
import ReactStars from 'react-stars'
import AddFeedbackButton from '../../components/feedback/newFeedbackBtn';
import "../ContactMePage/contact.css";
import { useTranslation } from "react-i18next";
function Home() {
    const {
        isAuthenticated,
        user
      } = useAuth0();

    const [editMode, setEditMode] = useState(false);
    const { t } = useTranslation('contactMe');
    
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
    
    const [feedbackDataToSend, setFeedbackDataToSend] = useState({});
    
    const handleInputChange = (e) => {
      const {name, value} = e?.target || {};
      const updatedData = {
        ...feedbackDataToSend,
        userId:RegexUserId,
        [name]: value,
    };
    console.log(updatedData)
    setFeedbackDataToSend(updatedData);
    };

    const handleStarsChange = (value) => {
        const updatedData = {
          ...feedbackDataToSend,
          userId:RegexUserId,
          stars: value,
      };
      console.log(updatedData)
      setFeedbackDataToSend(updatedData);
      };

      

return (
<div>
    {!isAuthenticated && <NavNotLoggedIn/>}
    {isAuthenticated && <NavLoggedIn/>}
    <div id="contactBackground">
        <div className="header-contain"> 
            <h1 className="white-title">{t('contactMeTitle')}</h1>      
        </div> 
        <RoleBasedSwitch user={user} role={ROLES.PERSONAL_TRAINER} onClick={() => setEditMode((prevEditMode) => !prevEditMode)}></RoleBasedSwitch>
        <div className="feedback-container">
            <div className="sidebar">
            <aside>
                <h4 className="contact-title">{t('myContactInfo')}</h4>
                <p className="contact-text">{t('phoneNumber')}: +189 789 667</p>
                <p className="contact-text">{t('emailAddress')}: sarah.fitness@gmail.com</p>
            </aside>
            </div>
            <div className="account-container">
                <h3>{t('provideFeedback')}!</h3>
                <ReactStars count={5} value={feedbackDataToSend.stars} onChange={handleStarsChange} size={30} half={false} color1="#ddd" color2={'#ffd700'} edit={true} />
                <div className="form-group">
                    <label htmlFor="content">{t('content')}</label>
                    <textarea id="content" placeholder={t('contentFiller')} name="content" required onChange={(e) => handleInputChange(e)} />
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
