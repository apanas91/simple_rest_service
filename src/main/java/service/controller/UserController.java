package service.controller;

import service.model.Users;
import service.repo.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @GetMapping
    public List<Users> getList() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public Users getUser(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public Users save(@RequestBody Users user) {
        return userService.createUser(user);
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
