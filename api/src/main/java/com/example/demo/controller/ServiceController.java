package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.openapitools.api.ApplicationsApi;
import org.openapitools.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import io.micrometer.common.lang.NonNull;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ServiceController implements ApplicationsApi {

    private final NativeWebRequest request;

    @Autowired
    public ServiceController(@NonNull NativeWebRequest request){
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @GetMapping("/applications")
    public ResponseEntity<List<Application>> getApplications() {
        // TODO Auto-generated method stub
        return ApplicationsApi.super.getApplications();
    }

}
