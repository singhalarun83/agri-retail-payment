package com.agri.payment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
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
	private RStreamEVMSale rStream;

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

@Embeddable
@Data
class RStreamEVMSale {

	@Column(name = "response_origin", nullable = true, length = 10)
	@JsonProperty("ResponseOrigin")
	private String responseOrigin;

	@Column(name = "dsix_return_code", nullable = true, length = 6)
	@JsonProperty("DSIXReturnCode")
	private String dsixReturnCode;

	@Column(name = "cmd_status", nullable = true, length = 10)
	@JsonProperty("CmdStatus")
	private String cmdStatus;

	@Column(name = "text_response", nullable = true, length = 255)
	@JsonProperty("TextResponse")
	private String textResponse;

	@Column(name = "sequence_no", nullable = true, length = 20)
	@JsonProperty("SequenceNo")
	private String sequenceNo;

	@Column(name = "user_trace", nullable = true, length = 40)
	@JsonProperty("UserTrace")
	private String userTrace;

	@Column(name = "merchant_id", nullable = true, length = 24)
	@JsonProperty("MerchantID")
	private String merchantId;

	@Column(name = "account_no", nullable = true, length = 24)
	@JsonProperty("AcctNo")
	private String accountNo;

	@Column(name = "card_type", nullable = true, length = 24)
	@JsonProperty("CardType")
	private String cardType;

	@Column(name = "tran_code", nullable = true, length = 40)
	@JsonProperty("TranCode")
	private String tranCode;

	@Column(name = "auth_code", nullable = true, length = 24)
	@JsonProperty("AuthCode")
	private String authCode;

	@Column(name = "capture_status", nullable = true, length = 24)
	@JsonProperty("CaptureStatus")
	private String captureStatus;

	@Column(name = "card_holder_id", nullable = true, length = 50)
	@JsonProperty("CardHolderID")
	private String cardHolderId;

	@Column(name = "invoice_no", nullable = true, length = 50)
	@JsonProperty("InvoiceNo")
	private String invoiceNo;

	@Column(name = "operator_id", nullable = true, length = 10)
	@JsonProperty("OperatorID")
	private String operatorId;

	@Column(name = "exp_date_month", nullable = true, length = 2)
	@JsonProperty("ExpDateMonth")
	private String expDateMonth;

	@Column(name = "exp_date_year", nullable = true, length = 2)
	@JsonProperty("ExpDateYear")
	private String expDateYear;

	@Column(name = "pay_api_id", nullable = true, length = 34)
	@JsonProperty("PayAPI_Id")
	private String payApiId;

	@Column(name = "processor_token", nullable = true, length = 14)
	@JsonProperty("ProcessorToken")
	private String processorToken;

	@Column(name = "purchase", nullable = true)
	@JsonProperty("Purchase")
	private BigDecimal purchase;

	@Column(name = "gratuity", nullable = true)
	@JsonProperty("Gratuity")
	private BigDecimal gratuity;

	@Column(name = "surcharge_with_lookup", nullable = true)
	@JsonProperty("SurchargeWithLookup")
	private BigDecimal surchargeWithLookup;

	@Column(name = "cashback", nullable = true)
	@JsonProperty("CashBack")
	private BigDecimal cashback;

	@Column(name = "tax", nullable = true)
	@JsonProperty("Tax")
	private BigDecimal tax;

	@Column(name = "authorize", nullable = true)
	@JsonProperty("Authorize")
	private BigDecimal authorize;

	@Column(name = "card_holder_name", nullable = true, length = 40)
	@JsonProperty("CardholderName")
	private String cardHolderName;

	@Column(name = "acq_ref_data", nullable = true, length = 200)
	@JsonProperty("AcqRefData")
	private String acqRefData;;

	@Column(name = "post_process", nullable = true, length = 40)
	@JsonProperty("PostProcess")
	private String postProcess;

	@Column(name = "process_data", nullable = true, length = 999)
	@JsonProperty("ProcessData")
	private String processData;

	@Column(name = "record_no", nullable = true, length = 256)
	@JsonProperty("RecordNo")
	private String recordNo;

	@Column(name = "entry_method", nullable = true, length = 40)
	@JsonProperty("EntryMethod")
	private String entryMethod;

	@Column(name = "date", nullable = true, length = 40)
	@JsonProperty("Date")
	private String date;

	@Column(name = "time", nullable = true, length = 40)
	@JsonProperty("Time")
	private String time;

	@Column(name = "application_label", nullable = true, length = 40)
	@JsonProperty("ApplicationLabel")
	private String applicationLabel;

	@Column(name = "aid", nullable = true, length = 40)
	@JsonProperty("AID")
	private String aid;

	@Column(name = "tvr", nullable = true, length = 40)
	@JsonProperty("TVR")
	private String tvr;

	@Column(name = "iad", nullable = true, length = 40)
	@JsonProperty("IAD")
	private String iad;

	@Column(name = "tsi", nullable = true, length = 40)
	@JsonProperty("TSI")
	private String tsi;

	@Column(name = "arc", nullable = true, length = 40)
	@JsonProperty("ARC")
	private String arc;

	@Column(name = "cvm", nullable = true, length = 40)
	@JsonProperty("CVM")
	private String cvm;

	@Column(name = "line1", nullable = true, length = 41)
	@JsonProperty("Line1")
	private String line1;

	@Column(name = "line2", nullable = true, length = 41)
	@JsonProperty("Line2")
	private String line2;

	@Column(name = "line3", nullable = true, length = 41)
	@JsonProperty("Line3")
	private String line3;

	@Column(name = "line4", nullable = true, length = 41)
	@JsonProperty("Line4")
	private String line4;

	@Column(name = "line5", nullable = true, length = 41)
	@JsonProperty("Line5")
	private String line5;

	@Column(name = "line6", nullable = true, length = 41)
	@JsonProperty("Line6")
	private String line6;

	@Column(name = "line7", nullable = true, length = 41)
	@JsonProperty("Line7")
	private String line7;

	@Column(name = "line8", nullable = true, length = 41)
	@JsonProperty("Line8")
	private String line8;

	@Column(name = "line9", nullable = true, length = 41)
	@JsonProperty("Line9")
	private String line9;

	@Column(name = "line10", nullable = true, length = 41)
	@JsonProperty("Line10")
	private String line10;

	@Column(name = "line11", nullable = true, length = 41)
	@JsonProperty("Line11")
	private String line11;

	@Column(name = "line12", nullable = true, length = 41)
	@JsonProperty("Line12")
	private String line12;

	@Column(name = "line13", nullable = true, length = 41)
	@JsonProperty("Line13")
	private String line13;

	@Column(name = "line14", nullable = true, length = 41)
	@JsonProperty("Line14")
	private String line14;

	@Column(name = "line15", nullable = true, length = 41)
	@JsonProperty("Line15")
	private String line15;

	@Column(name = "line16", nullable = true, length = 41)
	@JsonProperty("Line16")
	private String line16;

	@Column(name = "line17", nullable = true, length = 41)
	@JsonProperty("Line17")
	private String line17;

	@Column(name = "line18", nullable = true, length = 41)
	@JsonProperty("Line18")
	private String line18;

	@Column(name = "line19", nullable = true, length = 41)
	@JsonProperty("Line19")
	private String line19;

	@Column(name = "line20", nullable = true, length = 41)
	@JsonProperty("Line20")
	private String line20;

	@Column(name = "line21", nullable = true, length = 41)
	@JsonProperty("Line21")
	private String line21;

	@Column(name = "line22", nullable = true, length = 41)
	@JsonProperty("Line22")
	private String line22;

	@Column(name = "line23", nullable = true, length = 41)
	@JsonProperty("Line23")
	private String line23;

	@Column(name = "line24", nullable = true, length = 41)
	@JsonProperty("Line24")
	private String line24;

	@Column(name = "line25", nullable = true, length = 41)
	@JsonProperty("Line25")
	private String line25;

	@Column(name = "line26", nullable = true, length = 41)
	@JsonProperty("Line26")
	private String line26;

	@Column(name = "line27", nullable = true, length = 41)
	@JsonProperty("Line27")
	private String line27;

	@Column(name = "line28", nullable = true, length = 41)
	@JsonProperty("Line28")
	private String line28;

	@Column(name = "line29", nullable = true, length = 41)
	@JsonProperty("Line29")
	private String line29;

	@Column(name = "line30", nullable = true, length = 41)
	@JsonProperty("Line30")
	private String line30;

	@Column(name = "line31", nullable = true, length = 41)
	@JsonProperty("Line31")
	private String line31;

	@Column(name = "line32", nullable = true, length = 41)
	@JsonProperty("Line32")
	private String line32;

	@Column(name = "line33", nullable = true, length = 41)
	@JsonProperty("Line33")
	private String line33;

	@Column(name = "line34", nullable = true, length = 41)
	@JsonProperty("Line34")
	private String line34;

	@Column(name = "line35", nullable = true, length = 41)
	@JsonProperty("Line35")
	private String line35;

	@Column(name = "line36", nullable = true, length = 41)
	@JsonProperty("Line36")
	private String line36;

	@Column(name = "line37", nullable = true, length = 41)
	@JsonProperty("Line37")
	private String line37;

}
