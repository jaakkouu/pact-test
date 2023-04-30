package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.openapitools.model.Application;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    public List<Application> getApplications(){
        return new ArrayList<Application>();
    }

}