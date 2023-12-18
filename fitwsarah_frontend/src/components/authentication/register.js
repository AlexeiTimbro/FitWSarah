import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import '/app/src/components/navigation/NotLoggedIn/navNotLoggedIn.css'; 

const RegisterButton = () => {
  const { loginWithRedirect } = useAuth0();
  return <button className="signup-button"  onClick={() => loginWithRedirect({authorizationParams: { screen_hint: "signup"}})}>
  Sign Up
</button>
};

export default RegisterButton;
