import React from 'react';
import { isRole } from './authUtils';


const RoleBasedSwitch = ({ user, role}) => {
  if (isRole(user, role)) {
    return (
    <label class="switch">
        <input type="checkbox"></input>
        <span class="slider round"></span>
    </label>
    );
  }
  return null;
};

export default RoleBasedSwitch;