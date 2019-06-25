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

    Users tom;

    @Before
    public void initUsers(){
        this.tom = new Users("tom", "tomspass", false);
    }

    @After
    public void cleanRepo(){
        Users tomToDel = ur.findFirstByName("tom");
        if (tomToDel != null) { ur.delete(tomToDel); }
    }

    @Test
    public void createUser_existsInRepo() {

        uc.create(this.tom);
        
        Users encontrado = ur.findFirstByName(this.tom.getName());

        assertEquals("Prueba de creacion y persistencia de Usuario",this.tom.getName(),encontrado.getName());
    }
}