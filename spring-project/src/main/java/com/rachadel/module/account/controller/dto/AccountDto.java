package com.rachadel.module.account.controller.dto;

import org.springframework.beans.BeanUtils;

import com.rachadel.module.account.domain.Account;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Manoel Rachadel Neto
 * @since  13 de out. de 2021
 */

@Getter
@Setter
public class AccountDto {

	private String documentNumber;
	
    public AccountDto getAccountDTO(Account account) {
        BeanUtils.copyProperties(account, this);
        return this;
    }
}
