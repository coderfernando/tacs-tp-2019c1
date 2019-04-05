package ar.utn.edu.tacs;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class UserController {

    @SuppressWarnings("unused")
    @GetMapping("/getExample")
    public String exampleGet() {
        return "get Example ";
    }

    // Como usuario quiero poder crear una cuenta con user y pass. Nota: La cuenta es de nuestra aplicación, no de Foursquare. La aplicación manejará una única cuenta de Foursquare para acceder a los recursos necesarios.
    // Como usuario quiero hacer log in en la página
    // Como usuario quiero hacer log out en la página

}