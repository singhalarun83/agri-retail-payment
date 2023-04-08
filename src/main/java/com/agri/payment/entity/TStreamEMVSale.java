package com.agri.payment.entity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Embeddable
@Data
public class TStreamEMVSale {
	@Embedded
	@JsonProperty("Transaction")
	@Valid
	private TransactionEMVSale transaction;
}
