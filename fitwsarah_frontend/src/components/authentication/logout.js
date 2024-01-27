import { useAuth0 } from "@auth0/auth0-react";
import React from "react";
import { useLanguage } from "../../LanguageContext/LanguageContext.js";
import { useTranslation } from "react-i18next";

const LogoutButton = () => {
  const { logout } = useAuth0();
  const { t } = useTranslation();
  const { changeLanguage } = useLanguage();

  return (
    <button onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
      {t('logout')}
    </button>
  );
};

export default LogoutButton;