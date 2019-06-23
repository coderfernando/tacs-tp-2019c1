package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.config.SecurityConfiguration;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @Autowired
    Utils utils;

    @Autowired
    UserRepository repository;

    @Autowired
    SecurityConfiguration configuration;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
        user.setPassword(configuration.passwordEncoder().encode(user.getPassword()));
        user.setAdmin(false);//TODO establecer por signup si es o no admin
        user.setLastAccess(new Date(System.currentTimeMillis()));
        repository.save(user);
        return user;
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