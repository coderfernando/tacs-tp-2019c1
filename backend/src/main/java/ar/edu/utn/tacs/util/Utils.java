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

    private Users userLogged;

    public Users getLoggedUser (){
        if(userLogged != null){
            return getUserLogged();
        }
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        setUserLogged(userRepository.findFirstByName(username));
        return getUserLogged();
    }

    private Users getUserLogged() {
        return userLogged;
    }

    private void setUserLogged(Users userLogged) {
        this.userLogged = userLogged;
    }
}
