package ar.edu.utn.tacs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.utn.tacs.controller.AdminController;
import ar.edu.utn.tacs.controller.UserController;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AdminControllerTest {

    @Autowired
    UserController uc;

    @Autowired
    AdminController ac;

    @Autowired
    UserRepository ur;

    @Before
    public void cleanRepo(){
        ur.deleteAll();
    }
    
    @After
    private void createAdmin(){
        Users admin = new Users("admin", "welcome1", true);
        ur.save(admin);
    }

    @Test
    public void createUser_AppearsInAdminList() {

        Users tom = new Users("tom", "tomspass", false);
        uc.create(tom);
        
        List<Users> encontrados = ac.GetUsers();

        Users encontrado = new Users();

        for (Users us : encontrados) {
            if(us.getName().equals(tom.getName())){
                encontrado = us;
                break;
            }
        }

        assertEquals("Prueba de creacion y persistencia de Usuario",tom.getName(),encontrado.getName());
    }

    @Test
    public void listForCreatedUser_Is0() {

        Users newbie = new Users("Bran New", "JustCreated", false);

        ur.save(newbie);
        
        Users encontrado = ur.findFirstByName(newbie.getName());

        Long cero = (long) 0;

        assertEquals("Prueba usuario recien creado no tiene listas", cero, ac.getCountLists(encontrado.getId().toString()));
    }

    @Test
    public void lastAccessCreatedUser_Is0() {

        Users newbie = new Users("Bron New", "NewlyCreated", false);

        ur.save(newbie);
        
        Users encontrado = ur.findFirstByName(newbie.getName());

        assertNull("Prueba usuario recien creado no tiene fecha de acceso", ac.getLastAccess(encontrado.getId().toString()));
    }
}