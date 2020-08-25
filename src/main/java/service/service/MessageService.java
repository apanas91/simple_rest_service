package service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.dto.MessageDto;
import service.model.Message;
import service.model.Users;
import service.repo.MessageRepo;

import java.security.Principal;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    public Message save(MessageDto messageDto, Principal user) {
        Users sender = userService.getUserByUsername(user.getName());
        Message message = new Message(messageDto.getText(), sender);
        return messageRepo.save(message);
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
