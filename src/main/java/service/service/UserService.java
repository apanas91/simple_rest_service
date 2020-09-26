package service.service;

import org.springframework.beans.BeanUtils;
import service.exceptions.UserAlreadyExistsExceptions;
import service.model.Users;
import service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    public Users getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public Users getUserById(Long id) {
        Optional<Users> userFromDb = userRepo.findById(id);
//        if (userFromDb.isEmpty()){ //jdk11
        if (userFromDb == null){
             throw new UsernameNotFoundException("Username with id" + id + " not found");
        } else {
            return userFromDb.get();
        }

//        return userRepo.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users myUser = getUserByUsername(userName);
        if (myUser == null) {
            throw new UsernameNotFoundException("Unknown user: " + userName);
        }
        UserDetails user = User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(myUser.getRole())
                .build();
        return user;
    }

    public Users createUser(Users user) {
        Users fromDB = userRepo.findByUsername(user.getUsername());
        if (fromDB == null) {
            Users nUser = new Users();
            BeanUtils.copyProperties(user, nUser, "id", "role");
            nUser.setRole("USER");
            return userRepo.save(nUser);
        } else {
            throw new UserAlreadyExistsExceptions("User with username " + user.getUsername() + " already exists");
        }
    }

    public List<Users> findAll(){
        return userRepo.findAll();
    }
}