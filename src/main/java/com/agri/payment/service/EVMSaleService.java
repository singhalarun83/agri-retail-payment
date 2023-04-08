package com.agri.payment.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.ObjectUtils;
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

import com.agri.payment.entity.AmountEMVSale;
import com.agri.payment.entity.EVMSaleRequest;
import com.agri.payment.entity.EVMSaleResponse;
import com.agri.payment.repository.EVMSaleRequestRepository;
import com.agri.payment.repository.EVMSaleResponseRepository;
import com.agri.payment.util.TranCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			requestObj.postProcess();
			populateRecordForVoidSale(requestObj);
			log.debug("Request -> {}", printJson(requestObj));
			createEVMSaleRequest(requestObj); // save request
			HttpEntity<EVMSaleRequest> entity = new HttpEntity<>(requestObj, createHttpHeaders());
			ResponseEntity<EVMSaleResponse> responseObj = restTemplate.postForEntity(new URI(evmsaleUrl), entity,
					EVMSaleResponse.class);
			log.debug("Response -> {}", printJson(responseObj));
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

	private void populateRecordForVoidSale(EVMSaleRequest requestObj) {
		if (requestObj.getTStream().getTransaction().getTranCode().equalsIgnoreCase(TranCode.VOIDBYRECORD.getTranCode())
				&& ObjectUtils.isNotEmpty(requestObj.getTStream().getTransaction().getRecordNo())) {
			EVMSaleResponse data = evmSaleResponseRepository
					.findByrStream_RecordNo(requestObj.getTStream().getTransaction().getRecordNo());
			if (data != null) {
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getInvoiceNo())) {
					requestObj.getTStream().getTransaction().setInvoiceNo(data.getRStream().getInvoiceNo());
				}
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getRefNo())) {
					requestObj.getTStream().getTransaction().setRefNo(data.getRStream().getInvoiceNo());
				}
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getAuthCode())) {
					requestObj.getTStream().getTransaction().setAuthCode(data.getRStream().getAuthCode());
				}
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getProcessData())) {
					requestObj.getTStream().getTransaction().setProcessData(data.getRStream().getProcessData());
				}
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getAcqRefData())) {
					requestObj.getTStream().getTransaction().setAcqRefData(data.getRStream().getAcqRefData());
				}
				if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getAmount())
						|| ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getAmount().getPurchase())) {
					if (ObjectUtils.isEmpty(requestObj.getTStream().getTransaction().getAmount())) {
						AmountEMVSale amount = new AmountEMVSale();
						amount.setPurchase(data.getRStream().getPurchase());
						requestObj.getTStream().getTransaction().setAmount(amount);
					} else {
						requestObj.getTStream().getTransaction().getAmount()
								.setPurchase(data.getRStream().getPurchase());
					}

				}
			}
		}

	}

	private String printJson(Object object) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private HttpHeaders createHttpHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBasicAuth(evmsaleUsername, evmsalePassword);
		return headers;
	}
}
