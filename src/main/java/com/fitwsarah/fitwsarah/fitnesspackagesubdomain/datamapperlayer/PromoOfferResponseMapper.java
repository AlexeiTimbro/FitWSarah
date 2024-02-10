package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoOffers;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.PromoOfferResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromoOfferResponseMapper {
    @Mapping(expression = "java(promoOffers.getPromoIdentifier().getPromoId())", target = "promoId")
    PromoOfferResponseModel entityToResponseModel(PromoOffers promoOffers);

    List<PromoOfferResponseModel> entityListToResponseModelList(List<PromoOffers> promoOffers);
}
