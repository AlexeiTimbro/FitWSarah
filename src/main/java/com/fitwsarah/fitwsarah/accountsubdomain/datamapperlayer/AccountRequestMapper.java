package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping( expression = "java(accountIdentifier) ", target = "accountIdentifier", ignore = true)
    Account requestModelToEntity(AccountRequestModel accountRequestModel);
}
