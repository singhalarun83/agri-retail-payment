package com.agri.payment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.agri.payment.audit.Audit;
import com.agri.payment.audit.AuditListener;
import com.agri.payment.audit.Auditable;

import lombok.Data;

@Data
@Entity
@Table(name = "client")
@EntityListeners(AuditListener.class)
public class Client implements Auditable, Serializable, GenericEntity<Client> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(max = 30, min = 1)
	@Column(name = "client_id", nullable = false, length = 30)
	private String clientId;

	@NotNull
	@Size(max = 50, min = 5)
	@Column(name = "client_name", nullable = false, length = 50)
	private String clientName;

	@Size(max = 250, min = 10)
	@Column(name = "description", nullable = true, length = 250)
	private String description;

	@Embedded
	private Audit audit;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void update(Client source) {
		this.clientId = source.getClientId();
		this.clientName = source.getClientName();
		this.description = source.getDescription();
	}

	@Override
	public Client createNewInstance() {
		Client newInstance = new Client();
		newInstance.update(this);

		return newInstance;
	}
}
