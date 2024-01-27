import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import { useLanguage } from "../../LanguageContext/LanguageContext.js";
import { useTranslation } from "react-i18next";

const RegisterButton = () => {
  const { loginWithRedirect } = useAuth0();
  const { t } = useTranslation();
  const { changeLanguage } = useLanguage();
  return <button className="signup-button"  onClick={() => loginWithRedirect({authorizationParams: { screen_hint: "signup"}})}>
  {t('signup')}
</button>
};

export default RegisterButton;
