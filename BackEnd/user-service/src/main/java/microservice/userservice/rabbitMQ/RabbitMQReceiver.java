package microservice.userservice.rabbitMQ;

import microservice.userservice.model.User;
import microservice.userservice.repo.PrivateRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    @Autowired
    PrivateRepository privateRepository;


    @RabbitListener(queues = "queueDeleteuser")
    public void receiveUserDetails(Private user) {
        try {

            privateRepository.delete(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
