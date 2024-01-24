package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageRequestMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer.FitnessPackageResponseMapper;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageRequestModel;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.FitnessPackageResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FitnessPackageServiceImpl implements FitnessPackageService{

    private FitnessPackageRepository fitnessPackageRepository;
    private FitnessPackageResponseMapper fitnessPackageResponseMapper;
    private FitnessPackageRequestMapper fitnessPackageRequestMapper;

    public FitnessPackageServiceImpl(FitnessPackageRepository fitnessPackageRepository, FitnessPackageResponseMapper fitnessPackageResponseMapper, FitnessPackageRequestMapper fitnessPackageRequestMapper) {
        this.fitnessPackageRepository = fitnessPackageRepository;
        this.fitnessPackageResponseMapper = fitnessPackageResponseMapper;
        this.fitnessPackageRequestMapper = fitnessPackageRequestMapper;
    }

    @Override
    public List<FitnessPackageResponseModel> getAllFitnessPackages(){
        return fitnessPackageResponseMapper.entityListToResponseModelList(fitnessPackageRepository.findAll());
    }

    @Override
    public FitnessPackageResponseModel getFitnessPackageByFitnessPackageId(String fitnessPackageId) {
        return fitnessPackageResponseMapper.entityToResponseModel(fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(fitnessPackageId));
    }

    @Override
    public FitnessPackageResponseModel addFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel) {
        FitnessPackage fitnessPackage = fitnessPackageRequestMapper.requestModelToEntity(fitnessPackageRequestModel);
        FitnessPackage savedFitnessPackage = fitnessPackageRepository.save(fitnessPackage);
        return fitnessPackageResponseMapper.entityToResponseModel(savedFitnessPackage);
    }

    @Override
    public FitnessPackageResponseModel updateFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String fitnessPackageId) {
        return null;
    }

    @Override
    public void removeFitnessPackage(String fitnessPackageId) {

    }
}
