package com.agri.payment.repository;

import com.agri.payment.entity.EVMSaleResponse;

public interface EVMSaleResponseRepository extends GenericRepository<EVMSaleResponse> {
	EVMSaleResponse findByrStream_RecordNo(String recordNo);
}
