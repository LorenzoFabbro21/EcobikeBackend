package microservice.userservice.rabbitMQ;

import microservice.userservice.model.User;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import microservice.userservice.repo.DealerRepository;
import microservice.userservice.repo.PrivateRepository;
import microservice.userservice.service.DealerServiceImpl;
import microservice.userservice.service.PrivateServiceImpl;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    //@Autowired
    //PrivateRepository privateRepository;

    //@Autowired
    //DealerRepository dealerRepository;

    @Autowired
    PrivateServiceImpl privateService;

    @Autowired
    DealerServiceImpl dealerService;

    @RabbitListener(queues = "queueDeleteUser")
    public void receiveDeleteUser(Private user) {
        System.out.println("print inizio listener");
        try {
            privateService.deletePrivate(user.getId());
            System.out.println("print dopo delere private");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @RabbitListener(queues = "queueCreateDealer")
    public void receiveCreateUser(Dealer user) {
        try {
            dealerService.saveDealer(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
