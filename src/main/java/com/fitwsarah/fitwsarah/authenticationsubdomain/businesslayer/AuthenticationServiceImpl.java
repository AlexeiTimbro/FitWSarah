package com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationRequestModel;
import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements  AuthenticationService{

    @Override
    public List<AuthenticationResponseModel> getAllAuthentications() {
        return null;
    }

    @Override
    public AuthenticationResponseModel getAuthenticationByAuthenticationId(String authenticationId) {
        return null;
    }

    @Override
    public AuthenticationResponseModel addAuthentication(AuthenticationRequestModel authenticationRequestModel, String authenticationId) {
        return null;
    }

    @Override
    public AuthenticationResponseModel updateAuthentication(AuthenticationRequestModel authenticationRequestModel, String authenticationId) {
        return null;
    }

    @Override
    public void removeAuthentication(String authenticationId) {

    }
}
