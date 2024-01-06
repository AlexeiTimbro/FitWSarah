import { isRole } from './authUtils';
import { Link } from 'react-router-dom';

const RoleBasedLink = ({ user, role, to, children, className }) => {
  return isRole(user, role) ? <Link to={to} className={className}>{children}</Link> : null;
};

export default RoleBasedLink;
