package com.example.demo.controller;

import java.util.List;

import org.openapitools.api.ApplicationsApi;
import org.openapitools.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ApplicationService;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController implements ApplicationsApi {

    @Autowired
    ApplicationService applicationService;

    @Override
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getApplications() {
        final var response = applicationService.getApplications();

        return response.size() > 0 ? ResponseEntity.ok().body(response) : ResponseEntity.noContent().header("Content-Type", "application/json").build();
    }

}
