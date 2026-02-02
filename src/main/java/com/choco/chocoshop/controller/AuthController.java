package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.User;
import com.choco.chocoshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String>   register(@RequestBody User user,@RequestParam String confirmPassword) {
        if(!user.getPassword().equals(confirmPassword)){
            return ResponseEntity.badRequest().body("Lozinke se ne poklapaju!");
        }

        if(!user.getEmail().contains("@")){
            return ResponseEntity.badRequest().body("Email nije ispravan!");
        }

        user.setRegistrationDate(LocalDateTime.now());
        userService.register(user);

        return ResponseEntity.ok("Korisnik registrovan uspesno!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return userService.findByUsername(username)
                .map(user -> {
                    if(user.getPassword().equals(password)){
                        return ResponseEntity.ok("Uspesna prijava!");
                    }else {
                        return ResponseEntity.badRequest().body("Pogresna lozinka!");
                    }
                }).orElse(ResponseEntity.badRequest().body("Korisnik ne postoji!"));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok("Uspesna odjava!");
    }
}
