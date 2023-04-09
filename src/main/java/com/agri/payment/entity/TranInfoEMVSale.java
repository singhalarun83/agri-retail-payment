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
public class TranInfoEMVSale {
	@Size(max = 17, min = 1)
	@Column(name = "tran_customer_code", nullable = true, length = 17)
	@JsonProperty("CustomerCode")
	@ApiModelProperty(notes = "For Purchase Card Level II transactions– customer’s identifying information; such as PO number.", example = "A12345", required = false)
	private String customerCode; // For Purchase Card Level II transactions– customer’s identifying information;
									// such as PO number.
}
