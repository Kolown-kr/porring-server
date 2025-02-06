package com.kolown.porring.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final JoinService joinService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody JoinDto joinDto){
        JwtTokenDto token = joinService.loginByEmailAndPassword(joinDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto){
        joinService.joinByEmailAndPassword(joinDto);
        return ResponseEntity.ok("success");
    }

}
