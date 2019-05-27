package ar.edu.utn.tacs.services;

import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component

public class MongoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findFirstByName(username);
        if(user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.getName(), user.getPassword(), authorities);
    }
}