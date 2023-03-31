package com.agri.payment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agri.payment.entity.Client;
import com.agri.payment.repository.ClientRepository;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/api/client")
public class ClientController extends GenericController<Client> {

    public ClientController(ClientRepository clientRepository) {
        super(clientRepository);
    }
}
