import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import { useLanguage } from "../../LanguageContext/LanguageContext.js";
import { useTranslation } from "react-i18next";

const LoginButton = () => {
  const { loginWithRedirect } = useAuth0();
  const { t } = useTranslation();
  const { changeLanguage } = useLanguage();

  return <button onClick={() => loginWithRedirect()}>{t('login')}</button>;
};

export default LoginButton;