package com.fyp.companyinterfaceservice.client;

import com.fyp.companyinterfaceservice.model.Position;
import com.fyp.companyinterfaceservice.model.User;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(url = "${feign.url}", name = "${feign.company}")
public interface ProgradClient {

    String AUTH_TOKEN = "x-api-key";
    String bearerToken  = "development_token";

    // companies endpoint

    @GetMapping("/companies/all")
    List<User> getAllCompanies(@RequestHeader(AUTH_TOKEN) String bearerToken);

    @PostMapping("/companies/add")
    User add(@RequestBody User user);

    @PutMapping("/companies/update")
    User update(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody User user);

    @PostMapping("/companies/login")
    User login(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody User user);

    @GetMapping("/companies/findByEmail")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByEmail(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String email);

    @GetMapping("/companies/findByName")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByName(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String name);

    @GetMapping("/companies/findByToken")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByToken(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String token);

    // positions endpoint

    @PostMapping("/positions/add")
    Position addPosition(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody Position position);

    @PutMapping("/positions/update")
    User updatePosition(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody Position position);

    @GetMapping("/positions/getCompanyPositions/{companyId}")
    Set<Position> getCompanyPositions(@RequestHeader(AUTH_TOKEN) String bearerToken, @PathVariable Long companyId);

//    @PostMapping("/logout")
//    ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);
}
