package ar.utn.edu.tacs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class UserController {

    @SuppressWarnings("unused")
    @GetMapping("/getExample")
    public String exampleGet() {
        return "get Example ";
    }
}