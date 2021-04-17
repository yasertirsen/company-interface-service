package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.model.Resume;
import com.fyp.companyinterfaceservice.service.interfaces.ResumeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ProgradClient client;
    @Value("${token.secret}")
    private String secretToken;

    public ResumeServiceImpl(ProgradClient client) {
        this.client = client;
    }

    @Override
    public ResponseEntity<InputStreamResource> getCv(Long applicationId) {
        Resume resume = client.getCv(secretToken, applicationId);

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(
                        new ByteArrayInputStream(resume.getData())));
    }
}
