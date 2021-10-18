package com.rachadel.module.transaction.controller.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.domain.enumeration.OperationType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Manoel Rachadel Neto
 * @since 13 de out. de 2021
 */

@Getter
@Setter
public class TransactionDTO {

	private Long id;
	private BigDecimal amount;
	private Integer operationTypeId;

	
	public static TransactionDTO getTransactionDTO(Transaction transaction) {
		TransactionDTO transactionDTO = new TransactionDTO();
		BeanUtils.copyProperties(transaction, transactionDTO);
		transactionDTO.operationTypeId = transaction.getOperationType().getId();
		return transactionDTO;
	}

	//
	public static Transaction getTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();
		BeanUtils.copyProperties(transactionDTO, transaction);

		Integer operationTypeId = transactionDTO.getOperationTypeId();
		transaction.setOperationType(OperationType.getById(operationTypeId));

		return transaction;
	}

	public static List<Transaction> getListTransaction(List<TransactionDTO> lstTransactionDTO) {
		return lstTransactionDTO.stream()
								.map(transactionDTO -> getTransaction(transactionDTO))
								.collect(Collectors.toList());
	}
	
	public static List<TransactionDTO> getListTransactionDTO(List<Transaction> lstTransaction) {
		return lstTransaction.stream()
								.map(transaction -> getTransactionDTO(transaction))
								.collect(Collectors.toList());
	}
}
