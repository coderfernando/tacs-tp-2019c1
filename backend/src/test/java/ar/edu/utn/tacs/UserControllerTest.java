package ar.edu.utn.tacs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.utn.tacs.controller.UserController;
import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    UserController uc;
    
    @Autowired
    UserRepository ur;

    @Before
    public void cleanRepo(){
        ur.deleteAll();
    }

    @After
    public void createAdmin(){
        Users admin = new Users("admin", "$2a$10$AjHGc4x3Nez/p4ZpvFDWeO6FGxee/cVqj5KHHnHfuLnIOzC5ag4fm", true);
        ur.save(admin);
    }

    @Test
    public void createUser_existsInRepo() {

        Users tom = new Users("tom", "tomspass", false);
        
        uc.create(tom);
        
        Users encontrado = ur.findFirstByName(tom.getName());

        assertEquals("Prueba de creacion y persistencia de Usuario",tom.getName(),encontrado.getName());
    }
}