package service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import service.dto.MessageDto;
import service.exceptions.NotFountException;
import service.model.Message;
import service.model.Users;
import service.repo.MessageRepo;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    public Message save(MessageDto messageDto, Principal user) {
        Users sender = userService.getUserByUsername(user.getName());
        Optional<Users> recipient = userService.getUserById(messageDto.getRecipient());
        if (recipient.isEmpty()){
            throw new NotFountException();
        } else {
            Users us = recipient.get();
            Message message = new Message(messageDto.getText(), us, sender);
            return messageRepo.save(message);
        }
    }

    public Message update(Message messageFromDb, MessageDto message) {
        BeanUtils.copyProperties(message, messageFromDb, "id", "sender");
        return messageRepo.save(messageFromDb);
    }

    public List<Message> findAll() {
        return messageRepo.findAll();
    }

    public void delete(Message message) {
        messageRepo.delete(message);
    }
}
