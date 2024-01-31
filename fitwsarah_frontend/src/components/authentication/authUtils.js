import { useAuth0 } from '@auth0/auth0-react';

export const useGetAccessToken = () => {
  const { getAccessTokenSilently } = useAuth0();

  const getAccessToken = async () => {
    try {
      const token = await getAccessTokenSilently();
      return token;
    } catch (e) {
      console.error('Error getting access token:', e);
      return null;
    }
  };

  return getAccessToken;
};

export const isRole = (user, role) => {
  return user && user["https://fitwsarah.com/roles"].includes(role);
};
