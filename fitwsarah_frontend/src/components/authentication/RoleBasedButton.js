import React from 'react';
import { isRole } from './authUtils';

const RoleBasedButton = ({ user, role, onClick, children, className }) => {
  if (isRole(user, role)) {
    return (
      <button className={className} onClick={onClick}>
        {children}
      </button>
    );
  }
  return null;
};

export default RoleBasedButton;