package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.model.Application;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.service.interfaces.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.fyp.companyinterfaceservice.client.ProgradClient.bearerToken;

@Service
public class PositionServiceImpl implements PositionService {
    private final ProgradClient client;

    @Autowired
    public PositionServiceImpl(ProgradClient client) {
        this.client = client;
    }

    @Override
    public Position addPosition(Position position) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        position.setDate(dtf.format(LocalDateTime.now()));
        return client.addPosition(bearerToken, position);
    }

    @Override
    public Position updatePosition(Position position) {
        return client.updatePosition(bearerToken, position);
    }

    @Override
    public ResponseEntity<String> deletePosition(Long positionId) {
        return client.deletePosition(bearerToken, positionId);
    }

    @Override
    public List<Position> getCompanyPositions(Long id) {
        return client.getCompanyPositions(bearerToken, id);
    }

    @Override
    public List<Application> getApplications(Long positionId) {
        return client.getApplications(bearerToken, positionId);
    }
}
