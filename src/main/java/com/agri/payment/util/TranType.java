package com.agri.payment.util;

public enum TranType {
	CREDIT("Credit"), DEBIT("Debit"), GIFT("PrePaid"), EMPTY(" ");

	private final String tranType;

	public String getTranType() {
		return tranType;
	}

	private TranType(String tranType) {
		this.tranType = tranType;
	}
}
