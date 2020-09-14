package service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})
@Data
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserView.class)
    private Long id;

    @JsonView(Views.UserView.class)
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    @JsonView(Views.UserView.class)
    private Users sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonView(Views.UserView.class)
    @JoinColumn(name = "recipient_id")
    private Users recipient;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView(Views.UserView.class)
    private LocalDateTime creationDate;

    public Message(String text) {
        this.text = text;
    }

    public Message(String text, Users recipient, Users sender) {
        this.text = text;
        this.recipient = recipient;
        this.sender = sender;
    }
}
