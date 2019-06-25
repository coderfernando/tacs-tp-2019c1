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

    Users tom;
    Users newbie;
    Users newbie2;

    @Before
    public void initUsers(){
        this.tom = new Users("tom", "tomspass", false);
        this.newbie = new Users("Bran New", "JustCreated", false);
        this.newbie2 = new Users("Bron New", "NewlyCreated", false);
    }

    @After
    public void cleanRepo(){
        Users tomToDel = ur.findFirstByName("tom");
        Users newbieToDel = ur.findFirstByName("Bran New");
        Users newbie2ToDel = ur.findFirstByName("Bron New");
        if (tomToDel != null) { ur.delete(tomToDel); }
        if (newbieToDel != null) { ur.delete(newbieToDel); }
        if (newbie2ToDel != null) { ur.delete(newbie2ToDel); }
    }

    @Test
    public void createUser_AppearsInAdminList() {
        
        uc.create(this.tom);
        
        List<Users> encontrados = ac.GetUsers();

        Users encontrado = new Users();

        for (Users us : encontrados) {
            if(us.getName().equals(this.tom.getName())){
                encontrado = us;
                break;
            }
        }

        assertEquals("Prueba de creacion y persistencia de Usuario",this.tom.getName(),encontrado.getName());
    }

    @Test
    public void listForCreatedUser_Is0() {

        ur.save(this.newbie);
        
        Users encontrado = ur.findFirstByName(this.newbie.getName());

        Long cero = (long) 0;

        assertEquals("Prueba usuario recien creado no tiene listas", cero, ac.getCountLists(encontrado.getId().toString()));
    }

    @Test
    public void lastAccessCreatedUser_Is0() {
        
        ur.save(this.newbie2);
        
        Users encontrado = ur.findFirstByName(this.newbie2.getName());

        assertNull("Prueba usuario recien creado no tiene fecha de acceso", ac.getLastAccess(encontrado.getId().toString()));
    }
}