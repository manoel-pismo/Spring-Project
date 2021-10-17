package com.rachadel.module.transaction.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/** <pre> 
 * CASH_PURCHASE            --> [0]COMPRA_A_VISTA 
 * PURCHASE_IN_INSTALLMENTS --> [1]COMPRA PARCELADA
 * WITHDRAW                 --> [2]SAQUE
 * PAYMENT                  --> [3]PAGAMENTO</pre>
 * 
 * @author Manoel Rachadel Neto
 * @since 12 de out. de 2021
 */

@Getter
@AllArgsConstructor
public enum OperationType {

	CASH_PURCHASE(0, "COMPRA A VSTA"), PURCHASE_IN_INSTALLMENTS(1, "COMPRA PARCELADA"), WITHDRAW(2, "SAQUE"),
	PAYMENT(3, "PAGAMENTO");

	private Integer id;
	private String description;
}
