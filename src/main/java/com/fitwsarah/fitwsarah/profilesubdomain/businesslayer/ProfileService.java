package com.fitwsarah.fitwsarah.profilesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import com.fitwsarah.fitwsarah.profilesubdomain.presentationlayer.ProfileRequestModel;
import com.fitwsarah.fitwsarah.profilesubdomain.presentationlayer.ProfileResponseModel;

import java.util.List;

public interface ProfileService {

    List<ProfileResponseModel> getAllProfiles();

    ProfileResponseModel getProfileByProfileId(String profileId);

    ProfileResponseModel addProfile(ProfileRequestModel profileRequestModel, String profileId);

    ProfileResponseModel updateProfile(ProfileRequestModel profileRequestModel, String profileId);

    void removeProfile(String profileId);
}
