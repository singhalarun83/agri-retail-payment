package com.agri.payment.entity;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.agri.payment.audit.Audit;
import com.agri.payment.audit.AuditListener;
import com.agri.payment.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "evm_sale_response")
@EntityListeners(AuditListener.class)
public class EVMSaleResponse implements Auditable, Serializable, GenericEntity<EVMSaleResponse> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Embedded
	@JsonProperty("RStream")
	private RStreamEMVSale rStream;

	@Embedded
	@JsonIgnore
	private Audit audit;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void update(EVMSaleResponse source) {
		BeanUtils.copyProperties(source, this, "id");
	}

	@Override
	public EVMSaleResponse createNewInstance() {
		EVMSaleResponse newInstance = new EVMSaleResponse();
		newInstance.update(this);

		return newInstance;
	}

}
