package com.fyp.companyinterfaceservice.controller;

import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.model.Application;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.service.interfaces.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PositionController {
    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/getCompanyPositions/{companyId}")
    public List<Position> getCompanyPositions(@PathVariable Long companyId) {
        return positionService.getCompanyPositions(companyId);
    }

    @PostMapping("/positions/add")
    public Position addPosition(@RequestBody Position position) throws ProgradException {
        return positionService.addPosition(position);
    }

    @PutMapping("/positions/update")
    public Position update(@RequestBody Position position) {
        return positionService.updatePosition(position);
    }

    @DeleteMapping("/positions/delete/{positionId}")
    public ResponseEntity<String> delete(@PathVariable Long positionId) {
        return positionService.deletePosition(positionId);
    }

    @GetMapping("/positions/getPositionApplications/{positionId}")
    public List<Application> getApplications(@PathVariable Long positionId) {
        return positionService.getApplications(positionId);
    }

    @PutMapping("/application/update")
    public Application updateApplication(@RequestBody Application application, @RequestParam String message) throws ProgradException {
        return positionService.updateApplication(application, message);
    }
}
