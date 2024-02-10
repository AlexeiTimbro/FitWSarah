package com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.datalayer.PromoOffers;
import com.fitwsarah.fitwsarah.fitnesspackagesubdomain.presentationlayer.PromoOfferRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PromoOfferRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(promoIdentifier) ", target = "promoIdentifier", ignore = true)
    PromoOffers requestModelToEntity(PromoOfferRequestModel promoOfferRequestModel);
}
