package ar.edu.utn.tacs.model;

import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;

public class User {

    @Id
    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String password;

    public User() { }

    public long getId() { return this.id; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

}
