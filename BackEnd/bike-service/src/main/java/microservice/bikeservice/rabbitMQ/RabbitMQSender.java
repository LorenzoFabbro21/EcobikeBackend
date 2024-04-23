package microservice.bikeservice.rabbitMQ;

import microservice.bikeservice.dto.AdRent;
import microservice.bikeservice.dto.AdSell;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQSender {

    @Autowired
    private final RabbitTemplate rabbitTemplate;

    @Qualifier("queueAdSell")
    @Autowired
    private final Queue queueAdSell;


    @Qualifier("queueAdRent")
    @Autowired
    private final Queue queueAdRent;


    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueAdSell") Queue queueAdSell, @Qualifier("queueAdRent") Queue queueAdRent) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueAdSell = queueAdSell;
        this.queueAdRent = queueAdRent;
    }

    public void sendAddBikeAdSell(AdSell adSell) {
        rabbitTemplate.convertAndSend(this.queueAdSell.getName(), adSell);
    }

    public void sendAddBikeAdRent(AdRent adRent) {
        rabbitTemplate.convertAndSend(this.queueAdRent.getName(), adRent);
    }
}
