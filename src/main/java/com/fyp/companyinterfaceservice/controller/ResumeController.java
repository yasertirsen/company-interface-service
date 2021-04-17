package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.service.interfaces.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping("getCv/{applicationId}")
    public ResponseEntity<InputStreamResource> getCv(@PathVariable Long applicationId) {
        return resumeService.getCv(applicationId);
    }
}
