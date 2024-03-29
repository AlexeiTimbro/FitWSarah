import React from 'react';
import { isRole } from './authUtils';
import "../authentication/switch.css"
import { useTranslation } from "react-i18next";

const RoleBasedSwitch = ({ user, role, onClick}) => {
  const { t } = useTranslation('auth0');
  if (isRole(user, role)) {
    return (
      <div className="edit-mode-container">
      <label className="editLabel" for="editMode">{t('editMode')}</label>
        <label className="switch">
          <input type="checkbox" id="editMode" onClick={onClick}/>
        <span className="slider round"></span>
      </label>
    </div>
    );
  }
  return null;
};

export default RoleBasedSwitch;