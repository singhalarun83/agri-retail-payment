package com.agri.payment.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.agri.payment.entity.EVMSaleRequest;
import com.agri.payment.entity.EVMSaleResponse;
import com.agri.payment.repository.EVMSaleRequestRepository;
import com.agri.payment.repository.EVMSaleResponseRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EVMSaleService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${datacap.evmsale.url}")
	private String evmsaleUrl;

	@Value("${datacap.evmsale.username}")
	private String evmsaleUsername;

	@Value("${datacap.evmsale.password}")
	private String evmsalePassword;

	@Autowired
	EVMSaleRequestRepository evmSaleRequestRepository;

	@Autowired
	EVMSaleResponseRepository evmSaleResponseRepository;

	@Transactional
	public EVMSaleRequest createEVMSaleRequest(EVMSaleRequest newDomain) {
		EVMSaleRequest dbDomain = newDomain.createNewInstance();
		return evmSaleRequestRepository.save(dbDomain);
	}

	@Transactional
	public EVMSaleResponse createEVMSaleResponse(EVMSaleResponse newDomain) {
		EVMSaleResponse dbDomain = newDomain.createNewInstance();
		return evmSaleResponseRepository.save(dbDomain);
	}

	public EVMSaleResponse processPayment(EVMSaleRequest requestObj) {
		try {
			log.debug("Request -> {}", requestObj);
			createEVMSaleRequest(requestObj); // save request
			HttpEntity<EVMSaleRequest> entity = new HttpEntity<>(requestObj, createHttpHeaders());
			ResponseEntity<EVMSaleResponse> responseObj = restTemplate.postForEntity(new URI(evmsaleUrl), entity,
					EVMSaleResponse.class);
			log.debug("Response -> {}", responseObj);
			try {
				createEVMSaleResponse(responseObj.getBody());
			} catch (Exception ex) {
				log.error("Failed to insert into DB -> {}", requestObj);
				ex.printStackTrace(); // ignore DB insert if payment successful although this should not happen ever
			}
			return responseObj.getBody();
		} catch (RestClientException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(evmsaleUsername, evmsalePassword);
		return headers;
	}
}
