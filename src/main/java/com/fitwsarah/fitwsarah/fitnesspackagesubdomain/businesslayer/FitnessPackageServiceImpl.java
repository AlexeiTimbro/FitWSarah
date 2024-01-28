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
        existingFitnessPackage.setTitle(fitnessPackageRequestModel.getTitle());
        existingFitnessPackage.setDuration(fitnessPackageRequestModel.getDuration());
        existingFitnessPackage.setDescription(fitnessPackageRequestModel.getDescription());
        existingFitnessPackage.setOtherInformation(fitnessPackageRequestModel.getOtherInformation());
        existingFitnessPackage.setPrice(fitnessPackageRequestModel.getPrice());
        return fitnessPackageResponseMapper.entityToResponseModel(fitnessPackageRepository.save(existingFitnessPackage));
    }


    @Override
    public FitnessPackageResponseModel updateFitnessPackageStatus( String serviceId, String status) {
        FitnessPackage fitnessPackage = fitnessPackageRepository.findByFitnessPackageIdentifier_ServiceId(serviceId);
        fitnessPackage.setStatus(Status.valueOf(Status.INVISIBLE.toString()));
        fitnessPackageRepository.save(fitnessPackage);
        return fitnessPackageResponseMapper.entityToResponseModel(fitnessPackage);
    }


    @Override
    public void removeFitnessPackage(String fitnessPackageId) {

    }
}
