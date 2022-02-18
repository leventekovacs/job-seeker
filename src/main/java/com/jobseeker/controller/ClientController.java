package com.jobseeker.controller;

import com.jobseeker.model.Client;
import com.jobseeker.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class ClientController {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/client")
    public UUID registrateClient(@RequestBody Client client) {
        clientService.addClient(client);
        return client.getUuid();
    }
}
