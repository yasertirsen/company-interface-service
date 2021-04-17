package com.fyp.companyinterfaceservice.service.interfaces;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface ResumeService {

    ResponseEntity<InputStreamResource> getCv(Long applicationId);
}
