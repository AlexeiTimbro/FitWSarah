import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import { useTranslation } from "react-i18next";

const RegisterButton = () => {
  const { loginWithRedirect } = useAuth0();
  const { t } = useTranslation('auth0');
  return <button className="signup-button"  onClick={() => loginWithRedirect({authorizationParams: { screen_hint: "signup"}})}>
  {t('signup')}
</button>
};

export default RegisterButton;
