package ar.edu.utn.tacs.util;

import ar.edu.utn.tacs.model.Users;
import ar.edu.utn.tacs.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    @Autowired
    private UserRepository userRepository;

    public Users getLoggedUser (){
        try {
            String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return userRepository.findFirstByName(username);
        }
        catch (Exception e) {
            return null;
        }
    }

}
