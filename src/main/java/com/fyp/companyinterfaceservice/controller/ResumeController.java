package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.model.Resume;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

import static com.fyp.companyinterfaceservice.constant.Constants.SECRET_TOKEN;

@RestController
public class ResumeController {
    private final ProgradClient client;

    @Autowired
    public ResumeController(ProgradClient client) {
        this.client = client;
    }

    @GetMapping("getCv/{applicationId}")
    public ResponseEntity<InputStreamResource> getCv(@PathVariable Long applicationId) {
        Resume resume = client.getCv(SECRET_TOKEN, applicationId);

        return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(new InputStreamResource(
                            new ByteArrayInputStream(resume.getData())));
    }
}
