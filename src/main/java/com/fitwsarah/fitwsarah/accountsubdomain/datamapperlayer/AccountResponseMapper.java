package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountResponseMapper {
    AccountResponseModel entityToResponseModel(Account account);
    List<AccountResponseModel> entityListToResponseModelList(List<Account> accounts);

}
