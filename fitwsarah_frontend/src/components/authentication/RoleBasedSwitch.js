import React from 'react';
import { isRole } from './authUtils';
import "../authentication/switch.css"

const RoleBasedSwitch = ({ user, role, onClick}) => {
  if (isRole(user, role)) {
    return (
      <div className="edit-mode-container">
      <label className="editLabel" for="editMode">Edit Mode</label>
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