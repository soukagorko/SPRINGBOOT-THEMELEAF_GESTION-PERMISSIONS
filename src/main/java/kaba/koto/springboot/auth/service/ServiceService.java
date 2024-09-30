package kaba.koto.springboot.auth.service;

import kaba.koto.springboot.auth.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public List<kaba.koto.springboot.auth.entities.Service> getAllServices() {
        return serviceRepository.findAll();
    }
}
