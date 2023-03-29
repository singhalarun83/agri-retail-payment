package com.agri.payment.audit;

public interface Auditable {
	Audit getAudit();

	void setAudit(Audit audit);
}
