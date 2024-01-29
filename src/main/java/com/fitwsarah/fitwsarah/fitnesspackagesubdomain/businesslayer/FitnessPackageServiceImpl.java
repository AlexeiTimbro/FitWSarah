package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.businesslayer;


import com.fitwsarah.fitwsarah.appointmentsubdomain.datalayer.Appointment;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackage;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.FitnessPackageRepository;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.Status;
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
        fitnessPackage.setStatus(Status.VISIBLE);
        FitnessPackage savedFitnessPackage = fitnessPackageRepository.save(fitnessPackage);
        return fitnessPackageResponseMapper.entityToResponseModel(savedFitnessPackage);
    }

    @Override
    public FitnessPackageResponseModel updateFitnessPackage(FitnessPackageRequestModel fitnessPackageRequestModel, String serviceId) {
        FitnessPackage existingFitnessPackage = fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId((serviceId));
        if(existingFitnessPackage == null){
            return  null;
        }

        existingFitnessPackage.setStatus(fitnessPackageRequestModel.getStatus());
        existingFitnessPackage.setTitle_EN(fitnessPackageRequestModel.getTitle_EN());
        existingFitnessPackage.setTitle_FR(fitnessPackageRequestModel.getTitle_FR());
        existingFitnessPackage.setDuration(fitnessPackageRequestModel.getDuration());
        existingFitnessPackage.setDescription_EN(fitnessPackageRequestModel.getDescription_EN());
        existingFitnessPackage.setDescription_FR(fitnessPackageRequestModel.getDescription_FR());
        existingFitnessPackage.setOtherInformation_EN(fitnessPackageRequestModel.getOtherInformation_EN());
        existingFitnessPackage.setOtherInformation_FR(fitnessPackageRequestModel.getOtherInformation_FR());
        existingFitnessPackage.setPrice(fitnessPackageRequestModel.getPrice());
        return fitnessPackageResponseMapper.entityToResponseModel(fitnessPackageRepository.save(existingFitnessPackage));
    }




    @Override
    public void removeFitnessPackage(String fitnessPackageId) {

    }
}
