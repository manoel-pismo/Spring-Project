package com.rachadel.module.transaction.service;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rachadel.config.error.exception.ValidationErrorException;
import com.rachadel.module.account.service.AccountService;
import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.domain.enumeration.OperationType;
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

	@Autowired
	private AccountService accountService;

	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	// salvar
	public Transaction save(Transaction transaction) {
		var operationType = transaction.getOperationType();		
		var accountId = transaction.getAccount().getId();
		var amount = transaction.getAmount();		
		
		this.processTransaction(operationType, accountId, amount);	
		return transactionRepository.save(transaction);
	}

	// processar transação
	public void processTransaction(OperationType operationType, Long accountId, BigDecimal amount) {
		switch (operationType) {
		case CASH_PURCHASE:
			this.makePurchase(amount, accountId); // --> [0]COMPRA_A_VISTA
			break;
		case PURCHASE_IN_INSTALLMENTS: 
			this.makePurchase(amount, accountId); // --> [1]COMPRA PARCELADA 
			break;
		case WITHDRAW: 
			this.makeWithdral(amount, accountId); // --> [2]SAQUE
			break;
		case PAYMENT: 
			this.makePayment(amount, accountId);  // --> [3]PAGAMENTO
			break;
		default:
			throw new ValidationErrorException("invalid operation type.");
		}
	}

	// efetuar compra
	public void makePurchase(BigDecimal amount, Long accountId) {
		this.verifyValidPurchaseOperation(amount);

		var account = accountService.findById(accountId).get();
		this.accountService.verifyAccountHasBalance(account.getBalance(), amount);

		account.setBalance(account.getBalance().subtract(amount));
		accountService.save(account);
	}

	// efetuar saque
	public void makeWithdral(BigDecimal amount, Long accountId) {
		this.verifyValidWithdrawalOperation(amount);

		var account = accountService.findById(accountId).get();
		this.accountService.verifyAccountHasBalance(account.getBalance(), amount);

		account.setBalance(account.getBalance().subtract(amount));
		accountService.save(account);
	}

	// efetuar pagamento
	public void makePayment(BigDecimal amount, Long accountId) {
		this.verifyValidPaymentOperation(amount);

		var account = accountService.findById(accountId).get();
		account.setBalance(account.getBalance().add(amount));
		accountService.save(account);
	}

	// verificar operação de retirada válida
	// (msg: Operações do tipo saque devem ser registradas com uma quantia negativa)
	public void verifyValidWithdrawalOperation(BigDecimal amount) {
		this.verifyValidAmount(amount);
		if (amount.compareTo(BigDecimal.ZERO) == 1) { // (1 Maior Que)
			throw new ValidationErrorException("withdrawal type operations must be registered with a negative amount");
		}
	}

	// verificar operação de compra válida
	// (msg: As transações do tipo de compra devem ser registradas com uma quantia
	// negativa)
	public void verifyValidPurchaseOperation(BigDecimal amount) {
		this.verifyValidAmount(amount);
		if (amount.compareTo(BigDecimal.ZERO) == 1) { // (1 Maior Que)
			throw new ValidationErrorException("purchase type transactions must be registered with a negative amount");
		}
	}

	// verificar operação de pagamento válida
	// (msg: As transações de tipo de pagamento devem ser registradas com uma
	// quantia negativa)
	public void verifyValidPaymentOperation(BigDecimal amount) {
		this.verifyValidAmount(amount);
		if (amount.compareTo(BigDecimal.ZERO) == -1) { // (-1 Menor Que)
			throw new ValidationErrorException("payment type transactions must be registered with a positive amount");
		}
	}

	// verificar quantia válida (Msg: A quantia deve ser maior que zero)
	public void verifyValidAmount(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) == 0) { // (0 Igual Que)
			throw new ValidationErrorException("the amount must be greater than zero");
		}
	}
}
