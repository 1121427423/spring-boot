package com.springboot.oauth2.service;

import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("JdbcClientDetailsService")
public class JdbcClientDetailsServiceImpl implements ClientDetailsService, ClientRegistrationService {


    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        return null;
    }

    @Override
    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {

    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {

    }

    @Override
    public void updateClientSecret(String s, String s1) throws NoSuchClientException {

    }

    @Override
    public void removeClientDetails(String s) throws NoSuchClientException {

    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return null;
    }
}
