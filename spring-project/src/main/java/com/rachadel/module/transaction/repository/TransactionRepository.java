package com.rachadel.module.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rachadel.module.transaction.domain.Transaction;

/**
 * @author Manoel Rachadel Neto
 * @since  12 de out. de 2021
 */

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
