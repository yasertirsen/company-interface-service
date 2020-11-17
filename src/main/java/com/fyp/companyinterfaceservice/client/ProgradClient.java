package com.fyp.companyinterfaceservice.client;

import com.fyp.companyinterfaceservice.model.User;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "${feign.url}", name = "${feign.company}")
public interface ProgradClient {

    String AUTH_TOKEN = "x-api-key";
    String bearerToken  = "development_token";

    @GetMapping("/all")
    List<User> getAllCompanies(@RequestHeader(AUTH_TOKEN) String bearerToken);

    @PostMapping("/add")
    User add(@RequestBody User user);

    @PutMapping("/update")
    User update(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody User user);

    @PostMapping("/login")
    User login(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestBody User user);

    @GetMapping("/findByEmail")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByEmail(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String email);

    @GetMapping("/findByName")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByName(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String name);

    @GetMapping("/findByToken")
    @Headers({"Content-Type: application/json"})
    ResponseEntity<User> findByToken(@RequestHeader(AUTH_TOKEN) String bearerToken, @RequestParam String token);

//    @PostMapping("/logout")
//    ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);
}
