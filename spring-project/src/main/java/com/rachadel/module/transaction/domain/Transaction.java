package com.rachadel.module.transaction.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.rachadel.module.account.domain.Account;
import com.rachadel.module.transaction.domain.enumeration.OperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Manoel Rachadel Neto
 * @since 17 de out. de 2021
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne
	private Account account;
    
    @Column(nullable = false)
	private BigDecimal amount;
	
	@Column(nullable = false)    
	@Enumerated(EnumType.ORDINAL)
	private OperationType operationType;
	
	@Column(nullable = false)
	private Date eventDate;
}
