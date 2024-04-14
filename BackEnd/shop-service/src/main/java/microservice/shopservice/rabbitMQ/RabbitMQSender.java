package microservice.shopservice.rabbitMQ;

import microservice.shopservice.dto.User;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("queueDeleteUser")
    @Autowired
    private final Queue queueDeleteUser;





    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueDeleteUser") Queue queueDeleteUser) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueDeleteUser = queueDeleteUser;

    }

    public void sendDeleteUser(User user) {
        rabbitTemplate.convertAndSend(this.queueDeleteUser.getName(), user);
    }


}
