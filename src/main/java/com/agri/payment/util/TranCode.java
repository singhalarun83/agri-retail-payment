package com.agri.payment.util;

public enum TranCode {

	SALE("EMVSale"), RETURN("EMVReturn"), RESET("EMVPadReset"), ISSUE("EMVIssue"), BALANCE("EMVBalance"),
	VOID("EMVVoidSale"), VOIDBYRECORD("VoidSaleByRecordNo");

	private final String tranCode;

	public String getTranCode() {
		return tranCode;
	}

	private TranCode(String tranCode) {
		this.tranCode = tranCode;
	}
}
