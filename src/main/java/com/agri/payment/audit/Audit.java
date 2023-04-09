package com.agri.payment.audit;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Audit {
	@Column(name = "created_on", updatable = false, nullable = false)
	private LocalDateTime createdOn;

	@Column(name = "created_by", updatable = false, nullable = false)
	private String createdBy;

	@Column(name = "updated_on", insertable = false)
	private LocalDateTime updatedOn;

	@Column(name = "updated_by", insertable = false)
	private String updatedBy;
}
