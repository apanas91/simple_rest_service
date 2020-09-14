package service.repo;

import org.apache.catalina.User;
import service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import service.model.Users;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    //запрос входящих
    List<Message> findByRecipientIsAndSenderIs(Users recipient, Users sender);
    //Запрос исходящих сообщения
    List<Message> findBySenderIsAndRecipientIs(Users sender, Users recipient);
}
