package microservice.shopservice.rabbitMQ;

import microservice.shopservice.dto.Private;
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

    @Qualifier("queueCreateDealer")
    @Autowired
    private final Queue queueCreateDealer;



    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueDeleteUser") Queue queueDeleteUser, @Qualifier("queueCreateDealer") Queue queueCreateDealer) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueDeleteUser = queueDeleteUser;
        this.queueCreateDealer = queueCreateDealer;

    }

    public void sendDeleteUser(Private user) {
        System.out.println("print in rabbit sender");
        rabbitTemplate.convertAndSend(this.queueDeleteUser.getName(), user);
    }

    public void sendCreateDealer(Private user) {
        System.out.println("Print in rabbit sender create dealer");
        rabbitTemplate.convertAndSend(this.queueCreateDealer.getName(), user);
    }



}
