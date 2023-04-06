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
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;

import com.agri.payment.audit.Audit;
import com.agri.payment.audit.AuditListener;
import com.agri.payment.audit.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "evm_sale_request")
@EntityListeners(AuditListener.class)
public class EVMSaleRequest implements Auditable, Serializable, GenericEntity<EVMSaleRequest> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;

	@Embedded
	@JsonProperty("TStream")
	@Valid
	private TStream tStream;

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
}

@Embeddable
@Data
class TStream {
	@Embedded
	@JsonProperty("Transaction")
	@Valid
	private Transaction transaction;
}

@Embeddable
@Data
class Transaction {

	@NotNull
	@Size(max = 24, min = 1)
	@Column(name = "merchant_id", nullable = false, length = 24)
	@JsonProperty("MerchantID")
	@ApiModelProperty(notes = "Merchant identification assigned by processor.", example = "700000012262", required = true)
	private String merchantId; // Merchant identification assigned by processor.

	@Size(max = 24, min = 1)
	@Column(name = "operator_id", nullable = true, length = 24)
	@JsonProperty("OperatorID")
	@ApiModelProperty(notes = "Operator (clerk, server, etc.) associated with the transaction.", example = "TEST", required = false)
	private String operatorId; // Operator (clerk, server, etc.) associated with the transaction.

	@NotNull
	@Size(max = 99, min = 1)
	@Column(name = "pos_package_id", nullable = false, length = 99)
	@JsonProperty("POSPackageID")
	@ApiModelProperty(notes = "Include this tag with POS package name and version where the name and version are separated with a colon (“:”). Ex: EMVUSClient:1.26", example = "EMVUSClient:1.26", required = true)
	private String posPackageId; // Include this tag with POS package name and version where the name and version
									// are separated with a colon (“:”). Ex: EMVUSClient:1.26

	@Size(max = 40, min = 1)
	@Column(name = "secure_device", nullable = true, length = 40)
	@JsonProperty("SecureDevice")
	@ApiModelProperty(notes = "CloudEMV2", example = "CloudEMV2", required = false)
	private String secureDevice; // “CloudEMV2”

	@Size(max = 24, min = 1)
	@Column(name = "user_trace", nullable = true, length = 24)
	@JsonProperty("UserTrace")
	@ApiModelProperty(notes = "A unique value created and supplied by POS system", example = "Dev1", required = false)
	private String userTrace; // A unique value created and supplied by POS system

	@Size(max = 24, min = 3)
	@Column(name = "card_type", nullable = true, length = 24)
	@JsonProperty("CardType")
	@ApiModelProperty(notes = "“Credit” OR “Debit”. When an EMV card is presented, this will ensure only Credit or Debit processes (the user may still be presented with EMV Credit and Debit AIDs). For a swiped transaction, this field instructs the DC Direct device to select the transaction type without giving the cardholder to option to select at the PIN pad.", example = "Credit", required = false)
	private String cardType; // “Credit” OR “Debit”. When an EMV card is presented, this will ensure only
								// Credit or Debit processes (the user may still be presented with EMV Credit
								// and Debit AIDs). For a swiped transaction, this field instructs the DC Direct
								// device to select the transaction type without giving the cardholder to option
								// to select at the PIN pad.

	@NotNull
	@Size(max = 40, min = 1)
	@Column(name = "tran_code", nullable = false, length = 40)
	@JsonProperty("TranCode")
	@ApiModelProperty(notes = "The type of transaction (eg. sale, return ,void, etc)", example = "EMVSale", required = true)
	private String tranCode; // The type of transaction (eg. sale, return ,void, etc)`

	@Size(max = 14, min = 10)
	@Column(name = "processor_token", nullable = true, length = 14)
	@JsonProperty("ProcessorToken")
	@ApiModelProperty(notes = "The processor specific token returned by the processor. ProcessorToken is only supported with Chase today.", example = "TokenRequested", required = false)
	private String processorToken; // The processor specific token returned by the processor. ProcessorToken is
									// only supported with Chase today.

	@Size(max = 40, min = 1)
	@Column(name = "collect_data", nullable = true, length = 40)
	@JsonProperty("CollectData")
	@ApiModelProperty(notes = "“CardholderName” – To optionally extract the cardholder name from the card. If name is available, it will be returned in the tag in the response and the name will be included in the draft print output.", example = "CardholderName", required = false)
	private String collectData; // “CardholderName” – To optionally extract the cardholder name from the card.
								// If name is available, it will be returned in the tag in the response and the
								// name will be included in the draft print output.

	@Embedded
	@JsonProperty("Account")
	@Valid
	private Account account;

	@NotNull
	@Size(max = 50, min = 1)
	@Column(name = "invoice_no", nullable = false, length = 50)
	@JsonProperty("InvoiceNo")
	@ApiModelProperty(notes = "Invoice number - sequential receipt number, check number, or other unique transaction identifier created and supplied by POS system.", example = "0003", required = true)
	private String invoiceNo; // Invoice number - sequential receipt number, check number, or other unique
								// transaction identifier created and supplied by POS system.

	@NotNull
	@Size(max = 50, min = 1)
	@Column(name = "ref_no", nullable = false, length = 50)
	@JsonProperty("RefNo")
	@ApiModelProperty(notes = "Transaction reference number. Use the same data as InvoiceNo.", example = "0003", required = true)
	private String refNo; // Transaction reference number. Use the same data as InvoiceNo.

	@Size(max = 5, min = 1)
	@Column(name = "return_clear_exp_date", nullable = true, length = 5)
	@JsonProperty("ReturnClearExpDate")
	@ApiModelProperty(notes = "Use “Allow” to return unmasked card expiraton date in <ExpDateMonth> and <ExpDateYear>.", example = "Allow", required = false)
	private String returnClearExpDate; // Use “Allow” to return unmasked card expiraton date in <ExpDateMonth> and
										// <ExpDateYear>.
	@Embedded
	@JsonProperty("Amount")
	@Valid
	private Amount amount;

	@Column(name = "lane_id", nullable = true)
	@JsonProperty("LaneID")
	@ApiModelProperty(notes = "A unique LaneID (lane number) can be supplied to identify each POS/ECR in a single store location with multiple payment points", example = "1", required = false)
	private int laneId; // A unique LaneID (lane number) can be supplied to identify each POS/ECR in a
						// single store location with multiple payment points

	@Size(max = 24, min = 1)
	@Column(name = "duplicate", nullable = true, length = 24)
	@JsonProperty("Duplicate")
	@ApiModelProperty(notes = "“None” OR “Override” to force approval of a transaction that was previously rejected as a duplicate by the processor.", example = "None", required = false)
	private String duplicate; // “None” OR “Override” to force approval of a transaction that was previously
								// rejected as a duplicate by the processor.

	@Size(max = 20, min = 1)
	@Column(name = "sequence_no", nullable = true, length = 20)
	@JsonProperty("SequenceNo")
	@ApiModelProperty(notes = "Use the value returned for SequenceNo from the last response. If the SequenceNo is lost or for initial deployment of the PIN pad, the ECR/POS should attempt any transaction (using “0010010010” as a SequenceNo value) to re-sync. While this value is required, it’s unused in US EMV processing, and exists as a placeholder for future processing requirements.", example = "0010010010", required = false)
	private String sequenceNo; // Use the value returned for SequenceNo from the last response. If the
								// SequenceNo is lost or for initial deployment of the PIN pad, the ECR/POS
								// should attempt any transaction (using “0010010010” as a SequenceNo value) to
								// re-sync. While this value is required, it’s unused in US EMV processing, and
								// exists as a placeholder for future processing requirements.

	@Size(max = 256, min = 1)
	@Column(name = "record_no", nullable = true, length = 256)
	@JsonProperty("RecordNo")
	@ApiModelProperty(notes = "To obtain a token from the payment processor for use in subsequent transactions use the value “RecordNumberRequested”. Omit this tag if a token is not required.", example = "RecordNumberRequested", required = false)
	private String recordNo; // To obtain a token from the payment processor for use in subsequent
								// transactions use the value “RecordNumberRequested”. Omit this tag if a token
								// is not required.

	@Size(max = 40, min = 1)
	@Column(name = "frequency", nullable = true, length = 40)
	@JsonProperty("Frequency")
	@ApiModelProperty(notes = "To request a token that can be used only in other transactions in the current batch, use the value “OneTime”. To request a token that can be used more than once in other transactions in the current or subsequent batches, use the value “Recurring”. Omit this tag if <RecordNo> tag is not supplied.", example = "Recurring", required = false)
	private String frequency; // To request a token that can be used only in other transactions in the current
								// batch, use the value “OneTime”. To request a token that can be used more than
								// once in other transactions in the current or subsequent batches, use the
								// value “Recurring”. Omit this tag if <RecordNo> tag is not supplied.

	@NotNull
	@Size(max = 20, min = 1)
	@Column(name = "partial_auth", nullable = false, length = 20)
	@JsonProperty("PartialAuth")
	@ApiModelProperty(notes = "Use “Allow” to allow acceptance of partial authorizations.", example = "Allow", required = false)
	private String partialAuth; // Use “Allow” to allow acceptance of partial authorizations.

	@Size(max = 24, min = 1)
	@Column(name = "ok_amount", nullable = true, length = 24)
	@JsonProperty("OKAmount")
	@ApiModelProperty(notes = "When the optional OKAmount tag is included with the value “Disallow”, the PIN pad will not prompt the cardholder to approve the transaction amount. If the OKAmount tag is omitted, the PIN pad will ask the cardholder to approve the transaction amount by default.", example = "Disallow", required = false)
	private String okAmount; // When the optional OKAmount tag is included with the value “Disallow”, the PIN
								// pad will not prompt the cardholder to approve the transaction amount. If the
								// OKAmount tag is omitted, the PIN pad will ask the cardholder to approve the
								// transaction amount by default.

	@Embedded
	@JsonProperty("TranInfo")
	@Valid
	private TranInfo tranInfo;

	@Size(max = 24, min = 10)
	@Column(name = "tran_device_id", nullable = false, length = 24)
	@JsonProperty("TranDeviceID")
	@ApiModelProperty(notes = "The serial number on the device (for Ingenico TETRA pads it is 24 digits). TranDeviceID must be given either in the request body or as an HTTP header. TranDeviceID is required for Server communication.", example = "123456789098765432101234", required = false)
	private String tranDeviceId; // The serial number on the device (for Ingenico TETRA pads it is 24 digits).
									// TranDeviceID must be given either in the request body or as an HTTP header.
									// TranDeviceID is required for Server communication.

	@Size(max = 50, min = 40)
	@Column(name = "card_holder_id", nullable = true, length = 50)
	@JsonProperty("CardHolderID")
	@ApiModelProperty(notes = "Always send the value “Allow_V2” on transaction requests. The CardHolderID in the response is a unique ID representing the card data. The same card presented multiple times will always return the same CardHolderID.", example = "Allow_V2", required = false)
	private String cardHolderId; // Always send the value “Allow_V2” on transaction requests. The CardHolderID in
									// the response is a unique ID representing the card data. The same card
									// presented multiple times will always return the same CardHolderID.

	@Size(max = 75, min = 1)
	@Column(name = "merchant_defined_data", nullable = true, length = 75)
	@JsonProperty("MerchantDefinedData")
	@ApiModelProperty(notes = "Processor-specific pass through data field. Format and support varies by processor.", example = "subfield1                          subfield2           subfield3           ", required = false)
	private String merchantDefinedData; // Processor-specific pass through data field. Format and support varies by
										// processor.

	@Size(max = 99, min = 1)
	@Column(name = "payment_fee_description", nullable = true, length = 99)
	@JsonProperty("PaymentFeeDescription")
	@ApiModelProperty(notes = "Description used for <PaymentFee> (i.e. NON-CASH ADJ)", example = "NON-CASH ADJ", required = false)
	private String paymentFeeDescription; // Description used for <PaymentFee> (i.e. NON-CASH ADJ)

}

@Embeddable
@Data
class Account {
	@Size(max = 24, min = 1)
	@Column(name = "account_no", nullable = true, length = 24)
	@JsonProperty("AcctNo")
	@ApiModelProperty(notes = "Used to supply a manually keyed card number, or when the optional Account tag is included with the value “Prompt”, the PIN pad will prompt the operator for manual input of account number and expiration date.", example = "Prompt", required = false)
	private String accountNo; // Used to supply a manually keyed card number, or when the optional Account tag
								// is included with the value “Prompt”, the PIN pad will prompt the operator for
								// manual input of account number and expiration date.
}

@Embeddable
@Data
class Amount {
	@NotNull
	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_purchase", nullable = false)
	@JsonProperty("Purchase")
	@ApiModelProperty(notes = "Purchase price (with 2 place decimal – e.g. 29.95)", example = "7.50", required = true)
	private BigDecimal purchase; // Purchase price (with 2 place decimal – e.g. 29.95)

	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_payment_fee", nullable = true)
	@JsonProperty("PaymentFee")
	@ApiModelProperty(notes = "“Payment Fee” amount shown on the receipt. <PaymentFee> will only be included in the amount of the transaction if the value is added to the <Purchase> tag. On the receipt, you can provide your own description for the Payment Fee by using the <PaymentFeeDescription> tag. If <PaymentFeeDescription> is not provided, then “PAYMENT FEE” will be used on the receipt as the description.", example = "1.50", required = false)
	private BigDecimal paymentFee; // “Payment Fee” amount shown on the receipt. <PaymentFee> will only be included
	// in the amount of the transaction if the value is added to the <Purchase> tag.
	// On the receipt, you can provide your own description for the Payment Fee by
	// using the <PaymentFeeDescription> tag. If <PaymentFeeDescription> is not
	// provided, then “PAYMENT FEE” will be used on the receipt as the description.

	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_gratuity", nullable = true)
	@JsonProperty("Gratuity")
	@ApiModelProperty(notes = "Gratuity amount (with 2 decimal places – eg. 29.95) OR “Prompt” (which will cause the PIN pad to prompt the cardholder to input the gratuity amount) OR “PrintBlankLine” (which will return <PrintData> that includes blank tip and total lines) OR “SuggestivePrompt” (which, when paired with GratuitySuggestsions, will display custom gratuity options on the PIN pad)", example = "0.00", required = false)
	private BigDecimal gratuity; // Gratuity amount (with 2 decimal places – eg. 29.95) OR “Prompt” (which will
	// cause the PIN pad to prompt the cardholder to input the gratuity amount) OR
	// “PrintBlankLine” (which will return <PrintData> that includes blank tip and
	// total lines) OR “SuggestivePrompt” (which, when paired with
	// GratuitySuggestsions, will display custom gratuity options on the PIN pad)

	@Size(max = 100, min = 1)
	@Column(name = "amount_gratuity_suggestions", nullable = true, length = 100)
	@JsonProperty("GratuitySuggestions")
	@ApiModelProperty(notes = "When the Gratuity value is “SuggestivePrompt”, this field defines the values displayed in the custom gratuity buttons. Four values must be provided, using format “Button1-Label [colon] Gratuity1-Amount [comma] Button2-Label [colon] Gratuity2-Amount [comma] Button3-Label [colon] Gratuity3-Amount [comma] Button4-Label [colon] Gratuity4-Amount” (ex: 10%:1.00,15%:1.50,18%:1.80,20%:2.00). If one of the four buttons are pressed, the corresponding gratuity amount is used for the transaction. The gratuity entry form also has a button for “Other” and “No”. Pressing “Other” will cause the pad to display a prompt to manually enter a gratuity amount. Pressing “No” will cause the pad to skip any gratuity entry.", example = "10%:1.00,15%:1.50,18%:1.80,20%:2.00", required = false)
	private String gratuitySuggestions; // When the Gratuity value is “SuggestivePrompt”, this field defines the values
										// displayed in the custom gratuity buttons. Four values must be provided, using
										// format “Button1-Label [colon] Gratuity1-Amount [comma] Button2-Label [colon]
										// Gratuity2-Amount [comma] Button3-Label [colon] Gratuity3-Amount [comma]
										// Button4-Label [colon] Gratuity4-Amount” (ex:
										// 10%:1.00,15%:1.50,18%:1.80,20%:2.00). If one of the four buttons are pressed,
										// the corresponding gratuity amount is used for the transaction. The gratuity
										// entry form also has a button for “Other” and “No”. Pressing “Other” will
										// cause the pad to display a prompt to manually enter a gratuity amount.
										// Pressing “No” will cause the pad to skip any gratuity entry.

	@Size(max = 8, min = 1)
	@Column(name = "amount_cashback", nullable = true, length = 8)
	@JsonProperty("CashBack")
	@ApiModelProperty(notes = "Cash back amount with 2 decimal places (eg 29.95). Use “Prompt” to prompt the cardholder to input the cash back amount through the PIN pad.", example = "0.00", required = false)
	private String cashback; // Cash back amount with 2 decimal places (eg 29.95). Use “Prompt” to prompt the
								// cardholder to input the cash back amount through the PIN pad.

	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_maximum_cashback", nullable = true)
	@JsonProperty("MaximumCashBack")
	@ApiModelProperty(notes = "Limits the cash back amount when the CashBack tag is supplied with the value “Prompt” (with 2 decimal places – eg. 29.95).", example = "80.00", required = false)
	private BigDecimal maximumCashback; // Limits the cash back amount when the CashBack tag is supplied with the value
	// “Prompt” (with 2 decimal places – eg. 29.95).

	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_tax", nullable = true)
	@JsonProperty("Tax")
	@ApiModelProperty(notes = "For Purchase Card Level II transactions– Sales Tax amount (with 2 place decimal – eg. 29.95)", example = "0.00", required = false)
	private BigDecimal tax; // For Purchase Card Level II transactions– Sales Tax amount (with 2 place
	// decimal – eg. 29.95)

	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_surcharge_with_lookup", nullable = true)
	@JsonProperty("SurchargeWithLookup")
	@ApiModelProperty(notes = "Surcharge amount to apply to transaction. The surcharge will only be applied to non-debit transactions. See response field <SurchargeWithLookup> to determine if surcharge was applied. Requires NETePay Hosted.", example = "0.00", required = false)
	private BigDecimal surchargeWithLookup; // Surcharge amount to apply to transaction. The surcharge will only be
											// applied
	// to non-debit transactions. See response field <SurchargeWithLookup> to
	// determine if surcharge was applied. Requires NETePay Hosted.
}

@Embeddable
@Data
class TranInfo {
	@Size(max = 17, min = 1)
	@Column(name = "tran_customer_code", nullable = true, length = 17)
	@JsonProperty("CustomerCode")
	@ApiModelProperty(notes = "For Purchase Card Level II transactions– customer’s identifying information; such as PO number.", example = "A12345", required = false)
	private String customerCode; // For Purchase Card Level II transactions– customer’s identifying information;
									// such as PO number.
}
