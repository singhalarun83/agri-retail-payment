package com.agri.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agri.payment.entity.EVMSaleRequest;
import com.agri.payment.entity.EVMSaleResponse;
import com.agri.payment.service.EVMSaleService;

@RestController
@RequestMapping("/api/evmsale")
public class EVMSaleController {

	@Autowired
	private EVMSaleService evmSaleService;

	@PostMapping(path = "", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EVMSaleResponse> create(@RequestBody EVMSaleRequest requestObj) {
		EVMSaleResponse response = evmSaleService.processPayment(requestObj);
		return ResponseEntity.ok(response);
	}

}
