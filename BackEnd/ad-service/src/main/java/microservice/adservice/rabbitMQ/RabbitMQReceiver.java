package microservice.adservice.rabbitMQ;

import microservice.adservice.model.AdRent;
import microservice.adservice.model.AdSell;
import microservice.adservice.repo.AdSellRepository;
import microservice.adservice.repo.AdRentRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    @Autowired
    AdSellRepository adSellRepository;

    @Autowired
    AdRentRepository adRentRepository;

    @RabbitListener(queues = "queueAdSell")
    public void receiveAdSellDetails(AdSell adSell) {
        try {
            adSellRepository.save(adSell);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @RabbitListener(queues = "queueAdRent")
    public void receiveAdRentDetails(AdRent adRent) {
        try {
            adRentRepository.save(adRent);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
