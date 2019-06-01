package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.config.SecurityConfiguration;
import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserRepository repository;

    @Autowired
    SecurityConfiguration configuration;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
        user.setPassword(configuration.passwordEncoder().encode(user.getPassword()));
        user.setAdmin(false);//TODO establecer por signup si es o no admin
        repository.save(user);
        return user;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String logIn(@RequestBody Users user) {
        UserSession.getInstance().setUser(user);
        return "successful login!";
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public String logOut(@RequestBody Users user) {
        UserSession.getInstance().setUser(null);
        return "successful logout!";
    }

}