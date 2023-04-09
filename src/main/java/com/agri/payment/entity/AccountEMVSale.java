package com.agri.payment.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountEMVSale {
	@Size(max = 24, min = 1)
	@Column(name = "account_no", nullable = true, length = 24)
	@JsonProperty("AcctNo")
	@ApiModelProperty(notes = "Used to supply a manually keyed card number, or when the optional Account tag is included with the value “Prompt”, the PIN pad will prompt the operator for manual input of account number and expiration date.", example = "Prompt", required = false)
	private String accountNo; // Used to supply a manually keyed card number, or when the optional Account tag
								// is included with the value “Prompt”, the PIN pad will prompt the operator for
								// manual input of account number and expiration date.
}
