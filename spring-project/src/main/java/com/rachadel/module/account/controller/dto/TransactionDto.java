package com.rachadel.module.account.controller.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.rachadel.module.transaction.domain.Transaction;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Manoel Rachadel Neto
 * @since  13 de out. de 2021
 */

@Getter
@Setter
public class TransactionDto  {	

	private Long accountId;   
	private BigDecimal amount;
	private Integer operationTypeId;
	
    public TransactionDto getAccountDTO(Transaction transaction) {
        BeanUtils.copyProperties(transaction, this);
        return this;
    }
}
