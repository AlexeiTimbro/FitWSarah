package com.fitwsarah.fitwsarah.authenticationsubdomain.businesslayer;

import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationRequestModel;
import com.fitwsarah.fitwsarah.authenticationsubdomain.presentationlayer.AuthenticationResponseModel;

import java.util.List;

public interface AuthenticationService {

    List<AuthenticationResponseModel> getAllAuthentications();

    AuthenticationResponseModel getAuthenticationByAuthenticationId(String authenticationId);

    AuthenticationResponseModel addAuthentication(AuthenticationRequestModel authenticationRequestModel, String authenticationId);

    AuthenticationResponseModel updateAuthentication(AuthenticationRequestModel authenticationRequestModel, String authenticationId);

    void removeAuthentication(String authenticationId);
}
