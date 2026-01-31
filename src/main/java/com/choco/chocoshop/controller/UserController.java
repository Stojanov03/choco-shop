package com.choco.chocoshop.controller;

import com.choco.chocoshop.model.User;
import com.choco.chocoshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return  userService.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.register(user);
        return ResponseEntity.ok(savedUser);
    }
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.findById(id)
                .map(existringUser -> {
                    user.setId(id);
                    User updateUser = userService.update(user);
                    return ResponseEntity.ok(updateUser);
                }).orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        return userService.findById(id)
                .map(user -> {
                    userService.delete(user);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/profile/{username}")
    public ResponseEntity<User> getProfile(@PathVariable String username){
        return userService.findByUsername(username)
                .map(user -> {
                    user.setPassword(null);
                    return ResponseEntity.ok(user);
                }).orElse(ResponseEntity.notFound().build());
    }
    public ResponseEntity<String> updateProfile(@PathVariable String username,
                                                @RequestBody User updateUser,
                                                @RequestParam String newPassword,
                                                @RequestParam String confirmPassword
    ){
        return userService.findByUsername(username)
                .map(user -> {
                    user.setFirstName(updateUser.getFirstName());
                    user.setLastName(updateUser.getLastName());
                    user.setEmail(updateUser.getEmail());
                    user.setBirthDate(updateUser.getBirthDate());

                    if(newPassword != null && !newPassword.isEmpty()){
                        if(!newPassword.equals(confirmPassword)){
                            return ResponseEntity.badRequest().body("Lozinka se ne poklapa!");
                        }
                        user.setPassword(newPassword);
                    }
                    userService.update(user);
                    return ResponseEntity.ok("Profil uspesno azuriran!");
                }).orElse(ResponseEntity.notFound().build());
    }
}
