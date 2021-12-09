package in.out.tracker.InOutTracker.services;

import in.out.tracker.InOutTracker.model.User;
import in.out.tracker.InOutTracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

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

    public User createUser(User user) { return userRepository.save(user);}
}
