package com.agri.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import com.agri.payment.audit.Audit;
import com.agri.payment.audit.AuditListener;
import com.agri.payment.audit.Auditable;
import com.agri.payment.util.TranCode;
import com.agri.payment.util.TranType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "evm_sale_request")
@EntityListeners(AuditListener.class)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EVMSaleRequest implements Auditable, Serializable, GenericEntity<EVMSaleRequest> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Embedded
	@JsonProperty("TStream")
	@Valid
	private TStreamEMVSale tStream;

	@Embedded
	@JsonIgnore
	@Valid
	private Audit audit;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void update(EVMSaleRequest source) {
		BeanUtils.copyProperties(source, this, "id");
	}

	@Override
	public EVMSaleRequest createNewInstance() {
		EVMSaleRequest newInstance = new EVMSaleRequest();
		newInstance.update(this);

		return newInstance;
	}

	public void postProcess() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRefNo())
				&& !ObjectUtils.isEmpty(this.getTStream().getTransaction().getInvoiceNo())) {
			this.getTStream().getTransaction().setRefNo(this.getTStream().getTransaction().getInvoiceNo());
		}
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getUserTrace())) {
			this.getTStream().getTransaction().setUserTrace(RandomStringUtils.random(24, true, true));
		}
		String tranType = this.getTStream().getTransaction().getTranType();
		if (!ObjectUtils.isEmpty(tranType)) {
			if (tranType.equalsIgnoreCase(TranType.CREDIT.toString())) {
				this.getTStream().getTransaction().setTranType(TranType.CREDIT.getTranType());
			} else if (tranType.equalsIgnoreCase(TranType.DEBIT.toString())) {
				this.getTStream().getTransaction().setTranType(TranType.DEBIT.getTranType());
			} else if (tranType.equalsIgnoreCase(TranType.GIFT.toString())) {
				this.getTStream().getTransaction().setTranType(TranType.GIFT.getTranType());
			} else {
				this.getTStream().getTransaction().setTranType(TranType.EMPTY.getTranType());
			}
		}
		String tranCode = this.getTStream().getTransaction().getTranCode();
		if (!ObjectUtils.isEmpty(tranCode)) {
			if (tranCode.equalsIgnoreCase(TranCode.SALE.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.SALE.getTranCode());
				if (!ObjectUtils.isEmpty(this.getTStream().getTransaction().getTranType()) && this.getTStream()
						.getTransaction().getTranType().equalsIgnoreCase(TranType.GIFT.getTranType())) {
					populateGiftSale();
				} else {
					populateCardSale();
				}
			} else if (tranCode.equalsIgnoreCase(TranCode.RETURN.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.RETURN.getTranCode());
				if (!ObjectUtils.isEmpty(this.getTStream().getTransaction().getTranType()) && this.getTStream()
						.getTransaction().getTranType().equalsIgnoreCase(TranType.GIFT.getTranType())) {
					populateGiftReturn();
				} else {
					populateCardReturn();
				}
			} else if (tranCode.equalsIgnoreCase(TranCode.VOID.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.VOIDBYRECORD.getTranCode());
				if (!ObjectUtils.isEmpty(this.getTStream().getTransaction().getTranType()) && this.getTStream()
						.getTransaction().getTranType().equalsIgnoreCase(TranType.GIFT.getTranType())) {
					populateGiftSaleVoid();
				} else {
					populateCardSaleVoid();
				}
			} else if (tranCode.equalsIgnoreCase(TranCode.RESET.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.RESET.getTranCode());
				populateDeviceReset();
			} else if (tranCode.equalsIgnoreCase(TranCode.ISSUE.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.ISSUE.getTranCode());
				populateGiftIssue();
			} else if (tranCode.equalsIgnoreCase(TranCode.BALANCE.toString())) {
				this.getTStream().getTransaction().setTranCode(TranCode.BALANCE.getTranCode());
				populateGiftBalance();
			}
		}
	}

	private void populateDeviceReset() {
		this.getTStream().getTransaction().setTranType(null);
	}

	private void populateGiftIssue() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getPartialAuth()))
			this.getTStream().getTransaction().setPartialAuth("Allow");
	}

	private void populateGiftBalance() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getAmount())) {
			AmountEMVSale amount = new AmountEMVSale();
			amount.setPurchase(new BigDecimal(0.0));
			this.getTStream().getTransaction().setAmount(amount);
		}
	}

	private void populateGiftReturn() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
	}

	private void populateCardReturn() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getDuplicate()))
			this.getTStream().getTransaction().setDuplicate("None");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getOkAmount()))
			this.getTStream().getTransaction().setOkAmount("Disallow");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getCardHolderId()))
			this.getTStream().getTransaction().setCardHolderId("Allow_V2");
	}

	private void populateGiftSale() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getPartialAuth()))
			this.getTStream().getTransaction().setPartialAuth("Allow");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getDuplicate()))
			this.getTStream().getTransaction().setDuplicate("None");
	}

	private void populateCardSale() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getCollectData()))
			this.getTStream().getTransaction().setCollectData("CardholderName");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getDuplicate()))
			this.getTStream().getTransaction().setDuplicate("None");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getPartialAuth()))
			this.getTStream().getTransaction().setPartialAuth("Allow");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getOkAmount()))
			this.getTStream().getTransaction().setOkAmount("Disallow");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getRecordNo()))
			this.getTStream().getTransaction().setRecordNo("RecordNumberRequested");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("Recurring");
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getCardHolderId()))
			this.getTStream().getTransaction().setCardHolderId("Allow_V2");
	}

	private void populateGiftSaleVoid() {
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("OneTime");
	}

	private void populateCardSaleVoid() {
		this.getTStream().getTransaction().setTranType(TranType.CREDIT.getTranType());
		if (ObjectUtils.isEmpty(this.getTStream().getTransaction().getFrequency()))
			this.getTStream().getTransaction().setFrequency("OneTime");
	}
}
