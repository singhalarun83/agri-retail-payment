package com.agri.payment.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TStreamEMVSale {
	@Embedded
	@JsonProperty("Transaction")
	@Valid
	private TransactionEMVSale transaction;
}
