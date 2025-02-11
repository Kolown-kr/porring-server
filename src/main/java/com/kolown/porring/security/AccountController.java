package com.kolown.porring.security;

import com.kolown.porring.security.dto.JoinDto;
import com.kolown.porring.security.dto.JwtTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final EmailAccountService emailAccountService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody JoinDto joinDto){
        JwtTokenDto token = emailAccountService.loginByEmailAndPassword(joinDto);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto){
        emailAccountService.joinByEmailAndPassword(joinDto);
        return ResponseEntity.ok("success");
    }

}
