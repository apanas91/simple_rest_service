package service.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import service.dto.MessageDto;
import service.exceptions.NotFountException;
import service.model.Message;
import service.model.Users;
import service.repo.MessageRepo;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
        Users recipient = userService.getUserById(messageDto.getRecipient());
        if (recipient == null) {
            throw new NotFountException();
        } else {
            Message message = new Message();
            message.setText(messageDto.getText());
            message.setRecipient(recipient);
            message.setSender(sender);
            message.setCreationDate(LocalDateTime.now());
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

    public List<Message> getChat(Principal user, Users companion) {
        //олучаем объект Users по сессии
        Users us = userService.getUserByUsername(user.getName());

        List<Message> incommingMessages = getAllIncomingMessages(us, companion);
        List<Message> outgoingMessages = getAllOutgoingMessages(us, companion);

        List<Message> result = new ArrayList<Message>();
        result.addAll(outgoingMessages);
        result.addAll(incommingMessages);

        return result;
    }
    public List<Message> getAllIncomingMessages(Users user, Users companion) {
        return messageRepo.findByRecipientIsAndSenderIs(user, companion);
    }

    public List<Message> getAllOutgoingMessages(Users user, Users companion) {
        return messageRepo.findBySenderIsAndRecipientIs(user, companion);
    }
}
