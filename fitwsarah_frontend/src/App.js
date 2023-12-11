import logo from './logo.svg';
import './App.css';
import LoginButton from './components/authentication/login';
import LogoutButton from './components/authentication/logout';
import AccountProfile from './components/authentication/AccountProfile'

function App() {
  return (
    <>
    <LoginButton></LoginButton>
    <LogoutButton></LogoutButton>
    <AccountProfile></AccountProfile>
    </>
  );
}

export default App;
