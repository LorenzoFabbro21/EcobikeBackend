package microservice.adservice.rabbitMQ;

import microservice.adservice.model.AdRent;
import microservice.adservice.model.AdSell;
import microservice.adservice.repo.AdSellRepository;
import microservice.adservice.repo.AdRentRepository;
import microservice.adservice.service.AdRentService;
import microservice.adservice.service.AdSellService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {


    @Autowired
    AdSellService adSellService;

    @Autowired
    AdRentService adRentService;

    @RabbitListener(queues = "queueAdSell")
    public void receiveAdSellDetails(AdSell adSell) {
        try {
            adSellService.saveAdSell(adSell);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @RabbitListener(queues = "queueAdRent")
    public void receiveAdRentDetails(AdRent adRent) {
        try {
            adRentService.saveAdRent(adRent);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
