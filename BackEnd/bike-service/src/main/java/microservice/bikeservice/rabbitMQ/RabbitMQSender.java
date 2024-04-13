package microservice.bikeservice.rabbitMQ;

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

    @Qualifier("queueAd")
    @Autowired
    private final Queue queueAd;


    public RabbitMQSender(RabbitTemplate rabbitTemplate, @Qualifier("queueAd") Queue queueAd) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueAd = queueAd;
    }

    public void sendAddBikeAd(AdSell adSell) {
        rabbitTemplate.convertAndSend(this.queueAd.getName(), adSell);
    }
}
