package com.rachadel.config.error;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * @author Manoel Rachadel Neto
 * @since  14 de out. de 2021
 */

@SuperBuilder
@Getter
public class ResponseError {
	
	private int status;
	private Date timestamp;
	private List<ResponseErrorDetail> errors;

}
