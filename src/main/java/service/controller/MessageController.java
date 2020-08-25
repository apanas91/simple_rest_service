package service.controller;

import org.springframework.beans.BeanUtils;
import service.dto.MessageDto;
import service.model.Message;
import service.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.service.MessageService;
import service.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public List<Message> list() {
        return messageService.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message) {
        return message;
    }

    @PostMapping
    public Message create(
            Principal user,
            @RequestBody MessageDto messageDto){
        return messageService.save(messageDto, user);
    }

    @PutMapping("{id}")
    public Message update(
            @PathVariable("id") Message messageFromDb,
            @RequestBody MessageDto message
    ){
        return messageService.update(messageFromDb, message);
    }

    @DeleteMapping("{id}")
    public void delete (@PathVariable("id") Message message ){
        messageService.delete(message);
    }

}
