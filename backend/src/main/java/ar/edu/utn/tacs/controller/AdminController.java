package ar.edu.utn.tacs.controller;

import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.model.PlacesList;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    Utils utils;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("users/all")
    public List<Users> GetUsers() {

        List<Users> allUsers = userRepository.findAll();
        return allUsers;
    }

    @GetMapping("lists/all")
    public List<PlacesList> GetListsPlaces() {
        List<PlacesList> allLists = new ArrayList<>();
        PlacesList list1 = new PlacesList();
        PlacesList list2 = new PlacesList();

        list1.setName("list1");

        list2.setName("list2");

        allLists.add(list1);
        allLists.add(list2);

        return allLists;
    }
}

//    Como administrador quiero poder ver los siguientes datos de un usuario:
//        - Usuario
//        - Cantidad de listas
//        - Cantidad de lugares visitados en sus listas.
//        - Último acceso
//
//    Como administrador quiero seleccionar 2 listas de usuarios diferentes y ver si tienen algún lugar en común.
//    Como administrador quiero seleccionar un lugar y ver la cantidad de usuarios que se interesaron en el mismo (lo agregaron a una lista).
//    Como administrador quiero conocer la cantidad total de lugares registrados en el sistema
//        - En el día de hoy
//        - En los últimos 3 días
//        - En la última semana
//        - En el último mes
//        - Desde el inicio de los tiempos

