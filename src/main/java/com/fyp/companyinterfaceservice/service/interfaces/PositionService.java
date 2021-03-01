package com.fyp.companyinterfaceservice.service.interfaces;

import com.fyp.companyinterfaceservice.model.Application;
import com.fyp.companyinterfaceservice.model.Position;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PositionService {
    Position addPosition(Position position);
    Position updatePosition(Position position);
    ResponseEntity<String> deletePosition(Long positionId);
    List<Position> getCompanyPositions(Long id);

    List<Application> getApplications(Long positionId);
}
