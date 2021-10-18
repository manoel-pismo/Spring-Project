package com.rachadel.module.transaction.controller.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.rachadel.config.validation.annotations.ExistsId;
import com.rachadel.module.account.domain.Account;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.domain.enumeration.OperationType;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manoel Rachadel Neto
 * @since 13 de out. de 2021
 */

@Data
@NoArgsConstructor
public class TransactionDTO {
	
	@NotNull(message = "must not be null")
	@ExistsId(domainClass = Account.class, fieldName = "id")
	private Long accountId;
	
	@NotNull(message = "must not be null")
	private Integer operationTypeId;	
	
	@NotNull(message = "must not be null")
	@Positive(message = "the amount must be greater than zero")
	private BigDecimal amount;
	
	
	public TransactionDTO(Transaction transaction) {
		this.accountId = transaction.getAccount().getId();
		this.amount = transaction.getAmount();
		this.operationTypeId = transaction.getOperationType().getId();
	}
	
	public Transaction toModel(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		transaction.setAccount(new Account(this.accountId));
		transaction.setOperationType(OperationType.getById(operationTypeId));	
		transaction.setAmount(this.amount);
		return transaction;
	}
	
	public List<Transaction> getListTransaction(List<TransactionDTO> lstTransactionDTO) {
		return lstTransactionDTO.stream()
								.map(transactionDTO -> toModel(transactionDTO))
								.collect(Collectors.toList());
	}
	
	public static List<TransactionDTO> getListTransactionDTO(List<Transaction> lstTransaction) {
		return lstTransaction.stream()
								.map(transaction -> new TransactionDTO(transaction))
								.collect(Collectors.toList());
	}
}
