package com.kolown.porring.security;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final JoinService joinService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto){
        joinService.joinByEmailAndPassword(joinDto);
        return ResponseEntity.ok("success");
    }

}
