package com.rachadel.module.transaction.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rachadel.config.error.exception.ValidationErrorException;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.repository.TransactionRepository;

/**
 * @author Manoel Rachadel Neto
 * @since 12 de out. de 2021
 */

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	// encontrar todos
	public Page<Transaction> findAll(Pageable pageable) {
		return this.transactionRepository.findAll(pageable);
	}

	// salvar
	public Transaction save(Transaction transaction) {
		var amount = transaction.getAmount();
		
		switch (transaction.getOperationType()) {
		case CASH_PURCHASE:
		case PURCHASE_IN_INSTALLMENTS:
		case WITHDRAW:
			transaction.setAmount(amount.negate());
		case PAYMENT:
			break;
		default:
			throw new ValidationErrorException("invalid operation type.");
		}
		
		transaction.setEventDate(new Date());
		return transactionRepository.save(transaction);
	}
	
	// verificar quantia válida (Msg: A quantia deve ser maior que zero)
	public void verifyValidAmount(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) <= 0) { // (-1 Menor Que / 0 Igual Que)
			throw new ValidationErrorException("the amount must be greater than zero");
		}
	}		
}
