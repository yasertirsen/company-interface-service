package com.fyp.companyinterfaceservice.client;

import com.fyp.companyinterfaceservice.model.*;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${feign.url}", name = "${feign.company}")
public interface ProgradClient {

    String AUTH_TOKEN = "x-api-key";

    // companies endpoint

    @GetMapping("/companies/all")
    List<User> getAllCompanies(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN);

    @PostMapping("/companies/add")
    User add(@RequestBody User user);

    @PutMapping("/companies/update")
    User update(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestBody User user);

    @PostMapping("/companies/login")
    User login(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestBody User user);

    @GetMapping("/companies/findByEmail")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByEmail(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam String email);

    @GetMapping("/companies/findByName")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByName(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam String name);

    @GetMapping("/companies/findByToken")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByToken(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam String token);

    @GetMapping("/companies/mailingList")
    MailingList getMailingList(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam Long companyId);

    // positions endpoint

    @PostMapping("/positions/add")
    Position addPosition(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestBody Position position);

    @PutMapping("/positions/update")
    Position updatePosition(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestBody Position position);

    @GetMapping("/positions/getCompanyPositions/{companyId}")
    List<Position> getCompanyPositions(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @PathVariable Long companyId);

    @DeleteMapping("/positions/delete/{positionId}")
    ResponseEntity<String> deletePosition(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @PathVariable Long positionId);

    @GetMapping("/positions/getApplicationsByPositionId")
    List<Application> getApplications(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam Long positionId);

    @GetMapping("/positions/all")
    List<Position> getAllPositions(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN);

    @PutMapping("/positions/application/update")
    Application updateApplication(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestBody Application application);

    @GetMapping(value = "/positions/findById", produces = "application/json")
    Position findPositionById(@RequestHeader(AUTH_TOKEN) String secretToken, @RequestParam Long id);

    // files endpoint

    @GetMapping("/files/getCv")
    Resume getCv(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam Long applicationId);

    @GetMapping("/files/getImage")
    Image getStudentAvatar(@RequestHeader(AUTH_TOKEN) String secretToken, @RequestParam Long userId);

    // students endpoint

    @GetMapping("/students/findByEmail")
    Student getStudent(@RequestHeader(AUTH_TOKEN) String SECRET_TOKEN, @RequestParam String email);
}
