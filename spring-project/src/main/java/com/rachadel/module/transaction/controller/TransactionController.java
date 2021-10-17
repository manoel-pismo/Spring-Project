package com.rachadel.module.transaction.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rachadel.module.transaction.domain.Transaction;
import com.rachadel.module.transaction.service.TransactionService;

/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;

	@PostMapping()
	public ResponseEntity<?> save(@RequestBody @Valid Transaction transaction) {
		return new ResponseEntity<>(transactionService.save(transaction),HttpStatus.OK);
	}
}
