package org.sid.plutusvision.services;


import org.springframework.stereotype.Service;

public interface ClientService {

    String getFullNameById(Long id);

    Double getBalanceById(Long id);
}