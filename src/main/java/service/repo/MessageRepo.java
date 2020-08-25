package service.repo;

import service.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
//    List<Message> findBySenderAndRecipient(Long sender, Long recipient);
//    List<Message> findByRecipientAndSender(Long recipient, Long sender);

}
