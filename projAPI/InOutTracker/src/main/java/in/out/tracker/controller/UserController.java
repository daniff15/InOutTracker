package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.User;
import in.out.tracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://deti-engsoft-08:5500"})
@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/api/v1/users")
    public List<User> getAllUsers() { return service.getUsers(); }

    @GetMapping("/api/v1/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable(value = "id") long id)
            throws ResourceNotFoundException{
        return service.getUserById(id);
    }

    @PostMapping("/api/v1/users")
    public User addUser(@Valid @RequestBody User user) { return service.createUser(user); }

    @PostMapping("/api/v1/users/login")
    public Map<String, String> loginUser(@Valid @RequestBody User user) {
        return service.loginUser(user);
    }
}
