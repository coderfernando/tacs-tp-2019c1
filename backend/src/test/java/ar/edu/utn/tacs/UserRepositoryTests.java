package ar.edu.utn.tacs;

import static org.junit.Assert.assertEquals;

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

    @Before
    private void cleanRepo(){
        userRepository.deleteAll();
    }

    @Test
    public void whenFindFirstByName_thenReturnUser() {
        Users tomas = new Users("tomas", "passDeTomas", false);

        userRepository.save(tomas);
        
        Users encontrado = userRepository.findFirstByName(tomas.getName());

        assertEquals("Prueba de Persistencia de Usuario",tomas.getName(),encontrado.getName());
    }
}