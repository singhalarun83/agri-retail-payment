package com.agri.payment.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Embeddable
@Data
public class TransactionEMVSale {

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
	private String operatorId = "server"; // Operator (clerk, server, etc.) associated with the transaction.

	@Size(max = 99, min = 1)
	@Column(name = "pos_package_id", nullable = true, length = 99)
	@JsonProperty("POSPackageID")
	@ApiModelProperty(notes = "Include this tag with POS package name and version where the name and version are separated with a colon (“:”). Ex: EMVUSClient:1.26", example = "EMVUSClient:1.26", required = false)
	private String posPackageId = "EMVUSClient:1.26"; // Include this tag with POS package name and version where the
														// name and version
	// are separated with a colon (“:”). Ex: EMVUSClient:1.26

	@Size(max = 40, min = 1)
	@Column(name = "secure_device", nullable = true, length = 40)
	@JsonProperty("SecureDevice")
	@ApiModelProperty(notes = "CloudEMV2", example = "CloudEMV2", required = false)
	private String secureDevice = "CloudEMV2"; // “CloudEMV2”

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

	@Size(max = 40, min = 1)
	@Column(name = "tran_type", nullable = true, length = 40)
	@JsonProperty("TranType")
	@ApiModelProperty(notes = "Used with the TranCode to specify payment type.", example = "PrePaid", required = false)
	private String tranType; // Used with the TranCode to specify payment type.

	@NotNull
	@Size(max = 40, min = 1)
	@Column(name = "tran_code", nullable = false, length = 40)
	@JsonProperty("TranCode")
	@ApiModelProperty(notes = "The type of transaction (eg. sale, return ,void, etc)", example = "EMVSale", required = true)
	private String tranCode; // The type of transaction (eg. sale, return ,void, etc)

	@Column(name = "auth_code", nullable = true, length = 24)
	@JsonProperty("AuthCode")
	private String authCode;

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
	private String collectData; // “CardholderName” – To optionally extract the cardholder name from
								// the card.
	// If name is available, it will be returned in the tag in the response and the
	// name will be included in the draft print output.

	@Embedded
	@JsonProperty("Account")
	@Valid
	private AccountEMVSale account;

	@Size(max = 50, min = 1)
	@Column(name = "invoice_no", nullable = true, length = 50)
	@JsonProperty("InvoiceNo")
	@ApiModelProperty(notes = "Invoice number - sequential receipt number, check number, or other unique transaction identifier created and supplied by POS system.", example = "0003", required = false)
	private String invoiceNo; // Invoice number - sequential receipt number, check number, or other unique
								// transaction identifier created and supplied by POS system.

	@Size(max = 50, min = 1)
	@Column(name = "ref_no", nullable = true, length = 50)
	@JsonProperty("RefNo")
	@ApiModelProperty(notes = "Transaction reference number. Use the same data as InvoiceNo.", example = "0003", required = false)
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
	private AmountEMVSale amount;

	@Column(name = "lane_id", nullable = true)
	@JsonProperty("LaneID")
	@ApiModelProperty(notes = "A unique LaneID (lane number) can be supplied to identify each POS/ECR in a single store location with multiple payment points", example = "1", required = false)
	private Integer laneId; // A unique LaneID (lane number) can be supplied to identify each POS/ECR in a
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
	private String recordNo; // To obtain a token from the payment processor for use in
								// subsequent
	// transactions use the value “RecordNumberRequested”. Omit this tag if a token
	// is not required.

	@Size(max = 40, min = 1)
	@Column(name = "frequency", nullable = true, length = 40)
	@JsonProperty("Frequency")
	@ApiModelProperty(notes = "To request a token that can be used only in other transactions in the current batch, use the value “OneTime”. To request a token that can be used more than once in other transactions in the current or subsequent batches, use the value “Recurring”. Omit this tag if <RecordNo> tag is not supplied.", example = "Recurring", required = false)
	private String frequency; // To request a token that can be used only in other transactions in the
								// current
	// batch, use the value “OneTime”. To request a token that can be used more than
	// once in other transactions in the current or subsequent batches, use the
	// value “Recurring”. Omit this tag if <RecordNo> tag is not supplied.

	@Size(max = 20, min = 1)
	@Column(name = "partial_auth", nullable = true, length = 20)
	@JsonProperty("PartialAuth")
	@ApiModelProperty(notes = "Use “Allow” to allow acceptance of partial authorizations.", example = "Allow", required = false)
	private String partialAuth; // Use “Allow” to allow acceptance of partial authorizations.

	@Size(max = 24, min = 1)
	@Column(name = "ok_amount", nullable = true, length = 24)
	@JsonProperty("OKAmount")
	@ApiModelProperty(notes = "When the optional OKAmount tag is included with the value “Disallow”, the PIN pad will not prompt the cardholder to approve the transaction amount. If the OKAmount tag is omitted, the PIN pad will ask the cardholder to approve the transaction amount by default.", example = "Disallow", required = false)
	private String okAmount; // When the optional OKAmount tag is included with the value “Disallow”, the
								// PIN
	// pad will not prompt the cardholder to approve the transaction amount. If the
	// OKAmount tag is omitted, the PIN pad will ask the cardholder to approve the
	// transaction amount by default.

	@Embedded
	@JsonProperty("TranInfo")
	@Valid
	private TranInfoEMVSale tranInfo;

	@NotNull
	@Size(max = 24, min = 10)
	@Column(name = "tran_device_id", nullable = false, length = 24)
	@JsonProperty("TranDeviceID")
	@ApiModelProperty(notes = "The serial number on the device (for Ingenico TETRA pads it is 24 digits). TranDeviceID must be given either in the request body or as an HTTP header. TranDeviceID is required for Server communication.", example = "123456789098765432101234", required = true)
	private String tranDeviceId; // The serial number on the device (for Ingenico TETRA pads it is 24 digits).
									// TranDeviceID must be given either in the request body or as an HTTP header.
									// TranDeviceID is required for Server communication.

	@Size(max = 50, min = 1)
	@Column(name = "card_holder_id", nullable = true, length = 50)
	@JsonProperty("CardHolderID")
	@ApiModelProperty(notes = "Always send the value “Allow_V2” on transaction requests. The CardHolderID in the response is a unique ID representing the card data. The same card presented multiple times will always return the same CardHolderID.", example = "Allow_V2", required = false)
	private String cardHolderId; // Always send the value “Allow_V2” on transaction requests. The
									// CardHolderID in
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

	@Column(name = "process_data", nullable = true, length = 999)
	@JsonProperty("ProcessData")
	private String processData;

	@Column(name = "acq_ref_data", nullable = true, length = 200)
	@JsonProperty("AcqRefData")
	private String acqRefData;

}
