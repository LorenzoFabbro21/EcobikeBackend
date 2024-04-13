package microservice.adservice.rabbitMQ;

import microservice.adservice.model.AdSell;
import microservice.adservice.repo.AdSellRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    @Autowired
    AdSellRepository adSellRepository;

    @RabbitListener(queues = "queueAd")
    public void receiveAdDetails(AdSell adSell) {
        try {
            adSellRepository.save(adSell);
        } catch (Exception e) {
            System.err.println(e);
        }


    }
}
