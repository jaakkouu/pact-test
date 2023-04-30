package com.example.demo.controller;

import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.HttpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openapitools.model.Application;
import org.openapitools.model.Attachment;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.service.ApplicationService;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;

@Provider("MyProvider")
@Consumer("MyConsumer")
@PactBroker(host="localhost", port="9292")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceControllerPactTest {

    @MockBean
    private ApplicationService mockApplicationService;

    @LocalServerPort
    int port;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context, HttpRequest request) {
        request.addHeader("Accept", "application/json");
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State({"I have a list of applications"}) 
    public void listApplicationsState() {
        when(mockApplicationService.getApplications()).thenReturn(mockApplications());
    }

    @State({"I have no applications"}) 
    public void noApplicationsState() {
        final List<Application> list = new ArrayList<Application>();
        when(mockApplicationService.getApplications()).thenReturn(list);
    }

    private List<Application> mockApplications() {
        final List<Application> list = new ArrayList<Application>();

        for(int i = 0; i < (int) Math.round(Math.random() * 10); i++){
            final List<Attachment> listOfAttachments = new ArrayList<Attachment>();
            for(int x = 0; x < (int) Math.round(Math.random() * 3); x++){
                listOfAttachments.add(new Attachment().id(x).title("Title " + x).created(OffsetDateTime.now()));
            }
            list.add(new Application().id(i).title("Title " + i).created(OffsetDateTime.now()).attachments(listOfAttachments));
        }

        return list;
    }

}