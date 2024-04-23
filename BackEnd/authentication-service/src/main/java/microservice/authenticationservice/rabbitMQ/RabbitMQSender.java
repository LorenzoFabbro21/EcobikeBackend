package microservice.authenticationservice.rabbitMQ;

import microservice.authenticationservice.dto.Private;
import microservice.authenticationservice.dto.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {


    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("queueCreateUser")
    @Autowired
    private final Queue queueCreateUser;

    @Qualifier("queueSignUp")
    @Autowired
    private final Queue queueSignUp;

    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueCreateUser") Queue queueCreateUser, @Qualifier("queueSignUp") Queue queueSignUp) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueCreateUser = queueCreateUser;
        this.queueSignUp = queueSignUp;

    }

    public void sendCreateUser(User user) {
        System.out.println("sender auth prima di rabbit" + user.getName() + "  " + user.getLastName() + "  " + user.getMail() + "  " + user.getPassword() + "  " + user.getPhoneNumber() + "  ");

        rabbitTemplate.convertAndSend(this.queueCreateUser.getName(), user);
    }

    public void sendSignUp(User user) {
        System.out.println("sender auth prima di rabbit" + user.getName() + "  " + user.getLastName() + "  " + user.getMail() + "  " + user.getPassword() + "  " + user.getPhoneNumber() + "  ");

        rabbitTemplate.convertAndSend(this.queueSignUp.getName(), user);
    }


}
