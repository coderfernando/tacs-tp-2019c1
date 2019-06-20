package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.config.SecurityConfiguration;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @Autowired
    Utils utils;

    @Autowired
    UserRepository repository;

    @Autowired
    Utils utils;

    @Autowired
    SecurityConfiguration configuration;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
        user.setPassword(configuration.passwordEncoder().encode(user.getPassword()));
        user.setAdmin(false);//TODO establecer por signup si es o no admin
        user.setLastAccess(new Timestamp(System.currentTimeMillis()));
        repository.save(user);
        return user;
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> logIn(@RequestBody Users user) {
        Users savedUser = repository.findFirstByName(user.getName());
        if (savedUser != null && configuration.passwordEncoder().matches(user.getPassword(), savedUser.getPassword())) {
            UserSession.getInstance().setUser(savedUser);
            Users usr = utils.getLoggedUser();
            usr.setLastAccess(new Timestamp(System.currentTimeMillis()));
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logOut(@RequestBody Users user) {
        UserSession.getInstance().setUser(null);
        return "successful logout!";
    }

    @PostMapping("/me")
    @ResponseBody
    public Users me() {
        Users user = utils.getLoggedUser();
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

}