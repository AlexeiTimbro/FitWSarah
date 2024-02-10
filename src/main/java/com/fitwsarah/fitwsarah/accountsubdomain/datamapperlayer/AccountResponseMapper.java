package com.fitwsarah.fitwsarah.accountsubdomain.datamapperlayer;

import com.fitwsarah.fitwsarah.accountsubdomain.datalayer.Account;
import com.fitwsarah.fitwsarah.accountsubdomain.presentationlayer.AccountResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountResponseMapper {
    @Mapping(expression = "java(account.getAccountIdentifier().getAccountId())", target = "accountId")
    AccountResponseModel entityToResponseModel(Account account);
    List<AccountResponseModel> entityListToResponseModelList(List<Account> accounts);

}
