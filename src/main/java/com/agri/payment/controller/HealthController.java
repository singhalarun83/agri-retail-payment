package com.agri.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@RequestMapping("/")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("It's working");
	}
}
