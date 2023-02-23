package com.agri.payment.health;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@RequestMapping("/")
	public String hello() {
		return "It's working";
	}
}
