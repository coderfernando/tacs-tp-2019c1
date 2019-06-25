package ar.edu.utn.tacs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.utn.tacs.repositories.UserRepository;
import ar.edu.utn.tacs.model.Users;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserRepositoryTests {

    @Autowired
    UserRepository userRepository;

    Users tomas;

    @Before
    public void initUsers(){
        this.tomas = new Users("tomas", "passDeTomas", false);
    }

    @After
    public void cleanRepo(){
        Users tomasToDel = userRepository.findFirstByName("tomas");
        if (tomasToDel != null) { userRepository.delete(tomasToDel); }
    }

    @Test
    public void whenFindFirstByName_thenReturnUser() {
        userRepository.save(this.tomas);
        
        Users encontrado = userRepository.findFirstByName(this.tomas.getName());

        assertEquals("Prueba de Persistencia de Usuario",this.tomas.getName(),encontrado.getName());
    }
}