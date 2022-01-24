package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.User;
import in.out.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers(){ return userRepository.findAll(); }

    public ResponseEntity<User> getUserById(long Id)
            throws ResourceNotFoundException {
        User users = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + Id));
        return ResponseEntity.ok().body(users);
    }

    public User createUser(User user) {
        User dbUser = userRepository.findByEmail(user.getUsername());
        if (dbUser == null) return userRepository.save(user);
        return user;
    }

    public Map<String, String> loginUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User dbUser = userRepository.findByEmail(email);
        if(dbUser == null) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Email not found");
            return response;
        }

        if(!password.equals(dbUser.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Wrong password");
            return response;
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Login");
        response.put("type", String.valueOf(dbUser.getType()));
        response.put("name", dbUser.getName());
        response.put("username", dbUser.getUsername());
        response.put("email", dbUser.getEmail());
        response.put("id", String.valueOf(dbUser.getId()));

        return response;
    }
}
