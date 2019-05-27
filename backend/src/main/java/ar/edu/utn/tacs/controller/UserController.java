package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.model.UserSession;
import ar.edu.utn.tacs.model.Users;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("api/user")
public class UserController {

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Users create(@RequestBody Users user) {
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