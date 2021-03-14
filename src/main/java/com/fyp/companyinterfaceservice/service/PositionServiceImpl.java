package com.fyp.companyinterfaceservice.service;

import com.fyp.companyinterfaceservice.client.ProgradClient;
import com.fyp.companyinterfaceservice.exceptions.ProgradException;
import com.fyp.companyinterfaceservice.model.Application;
import com.fyp.companyinterfaceservice.model.MailingList;
import com.fyp.companyinterfaceservice.model.NotificationEmail;
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
    private final MailService mailService;

    @Autowired
    public PositionServiceImpl(ProgradClient client, MailService mailService) {
        this.client = client;
        this.mailService = mailService;
    }

    @Override
    public Position addPosition(Position position) throws ProgradException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        position.setDate(dtf.format(LocalDateTime.now()));
        position = client.addPosition(bearerToken, position);
        MailingList mailingList = client.getMailingList(bearerToken, position.getCompany().getCompanyId());
        if(mailingList != null) {
            for(String email : mailingList.getEmails()) {
                mailService.sendMail(new NotificationEmail("New Job Alert - " + position.getCompany().getName(),
                        email, "Hi,\n\n" +
                        position.getCompany().getName() + " has posted a new job - " + position.getTitle() +
                        "\nCheck it out here http://localhost:4202/job/" + position.getPositionId()));
            }
        }
        return position;
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
