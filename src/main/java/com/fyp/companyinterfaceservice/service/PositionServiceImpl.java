package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.model.Application;
import com.fyp.companyinterfaceservice.model.MailingList;
import com.fyp.companyinterfaceservice.model.NotificationEmail;
import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.service.interfaces.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    private final ProgradClient client;
    private final MailService mailService;
    @Value("${token.secret}")
    private String secretToken;

    @Autowired
    public PositionServiceImpl(ProgradClient client, MailService mailService) {
        this.client = client;
        this.mailService = mailService;
    }

    @Override
    public Position addPosition(Position position) throws ProgradException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        position.setDate(dtf.format(LocalDateTime.now()));
        position = client.addPosition(secretToken, position);
        MailingList mailingList = client.getMailingList(secretToken, position.getCompany().getCompanyId());
        if(mailingList != null) {
            for(String email : mailingList.getEmails()) {
                mailService.sendMail(new NotificationEmail("New Job Alert - " + position.getCompany().getName(),
                        email, position.getCompany().getName() + " has posted a new job - " + position.getTitle() +
                        " Check it out below.",  "http://localhost:4202/job/" + position.getPositionId()));
            }
        }
        return position;
    }

    @Override
    public Position updatePosition(Position position) {
        return client.updatePosition(secretToken, position);
    }

    @Override
    public ResponseEntity<String> deletePosition(Long positionId) {
        return client.deletePosition(secretToken, positionId);
    }

    @Override
    public List<Position> getCompanyPositions(Long id) {
        return client.getCompanyPositions(secretToken, id);
    }

    @Override
    public List<Application> getApplications(Long positionId) {
        return client.getApplications(secretToken, positionId);
    }

    @Override
    public Application updateApplication(Application application, String message) throws ProgradException {
        Position position = client.findPositionById(secretToken, application.getPositionId());
        mailService.sendMail(new NotificationEmail("Job Response - " + position.getTitle(),
                application.getEmail(), message, ""));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        application.setDate(dtf.format(LocalDateTime.now()));
        return client.updateApplication(secretToken, application);
    }
}
