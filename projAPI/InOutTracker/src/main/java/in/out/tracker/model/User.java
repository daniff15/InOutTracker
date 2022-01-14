package in.out.tracker.model;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    private long id;
    private int type;
    private String name;
    private String username;
    private String email;
    private String password;

    @Id
    @Column(name = "id", nullable = false)
    public long getId(){ return id; }
    public void setId(long id){ this.id = id; }

    @Column(name = "type", nullable = true)
    public int getType() { return type; }
    public void setType(int type) { this.type = type; }

    @Column(name = "name", nullable = true)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name = "username", nullable = true)
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Column(name = "email", nullable = true)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Column(name = "password", nullable = true)
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
