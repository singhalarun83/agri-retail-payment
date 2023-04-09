package com.agri.payment.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Embeddable
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AmountEMVSale {
	@Digits(integer = 9, fraction = 2)
	@Column(name = "amount_purchase", nullable = true)
	@JsonProperty("Purchase")
	@ApiModelProperty(notes = "Purchase price (with 2 place decimal – e.g. 29.95)", example = "7.50", required = false)
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
