package com.jobseeker.service;

import com.jobseeker.model.Client;
import com.jobseeker.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public void addClient(Client client) {
        if(isValidName(client.getName()) && isValidEmail(client.getEmail())) {
            clientRepository.save(client);
        }
    }

    protected boolean isValidName(String name) {
        if(name.length() > 100) throw new IllegalArgumentException("The given name is too long.");
        return true;
    }

    protected boolean isValidEmail(String email) {
        if(!email.matches("^(.+)@(.+)$"))
            throw new IllegalArgumentException("The given email does not suit syntax.");
        if(clientRepository.findByEmail(email).isPresent())
            throw new IllegalArgumentException("The given email is taken.");

        return true;
    }


}
