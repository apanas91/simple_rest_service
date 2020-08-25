package service.controller;

import service.model.Users;
import service.exceptions.RecordAlreadyExistsException;
import service.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Users> getList() {
        return userRepo.findAll();
    }

    @GetMapping("{id}")
    public Users getUser(@PathVariable long id) {
        return userRepo.findById(id);
    }

    @PostMapping
    public Users save(@RequestBody Users user) {
        Users fromDB = userRepo.findByUsername(user.getUsername());
        if (fromDB == null) {
            Users nUser = new Users();
            BeanUtils.copyProperties(user, nUser, "id", "role");
            nUser.setRole("USER");
            return userRepo.save(nUser);
        } else {
            throw new RecordAlreadyExistsException();
        }
    }

    @PutMapping("{id}")
    public Users update(@PathVariable("id") Users userFromDb,
                        @RequestBody Users user) {
        BeanUtils.copyProperties(user, userFromDb, "id", "role");
        return userRepo.save(userFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Users user) {
        userRepo.delete(user);
    }
}
