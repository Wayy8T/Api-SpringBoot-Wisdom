package com.example.CRUD_SpringBoot.mapper;
import com.example.CRUD_SpringBoot.dto.request.AccountRegisterRequest;
import com.example.CRUD_SpringBoot.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AccountMapper {
    Account toAccount(AccountRegisterRequest request);
}
