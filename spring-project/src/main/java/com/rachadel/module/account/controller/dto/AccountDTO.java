package com.rachadel.module.account.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import com.rachadel.module.account.domain.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manoel Rachadel Neto
 * @since  13 de out. de 2021
 */

@Data
@NoArgsConstructor
public class AccountDTO {
	
	@NotBlank(message = "must not be blan")
	@Column(length = 100)
	private String name;
	
	@NotBlank(message = "must not be blan")
	@Email(message = "must be a well-formed email address")
	@Column(length = 50)
	private String email;

	@NotBlank(message = "must not be blan")
	@CPF(message = "invalid Brazilian individual taxpayer registry number (CPF)")
	@Column(unique = true, nullable = false, length = 14)
	private String documentNumber;
	
	@NotNull
	private BigDecimal availableCreditLimit;
	

	public AccountDTO(Account account) {
		super();
		this.name = account.getName();
		this.email = account.getEmail();
		this.documentNumber = account.getDocumentNumber();
		this.availableCreditLimit = account.getAvailableCreditLimit();
	}	
	
	public Account toModel(AccountDTO accountDTO) {
		Account account = new Account();
		account.setName(this.getName());
		account.setEmail(this.getEmail());	
		account.setDocumentNumber(this.getDocumentNumber());
		account.setAvailableCreditLimit(this.getAvailableCreditLimit());
		return account;
	}
	
	public List<Account> getListAccount(List<AccountDTO> lstAccountDTO) {
		return lstAccountDTO.stream()
								.map(accountDTO -> toModel(accountDTO))
								.collect(Collectors.toList());
	}
	
	public static List<AccountDTO> getListAccountDTO(List<Account> lstAccount) {
		return lstAccount.stream()
								.map(account -> new AccountDTO(account))
								.collect(Collectors.toList());
	}
}
