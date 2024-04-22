package microservice.userservice.rabbitMQ;


import microservice.userservice.dto.User;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;

import microservice.userservice.service.DealerService;
import microservice.userservice.service.PrivateService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {

    @Autowired
    PrivateService privateService;

    @Autowired
    DealerService dealerService;

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
    public void receiveCreateDealer(Dealer user) {
        try {
            dealerService.saveDealer(user);
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    @RabbitListener(queues = "queueCreateUser")
    public void receiveCreateUser(User user) {
        try {
            Private p = new Private(user.getName(), user.getLastName(), user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getGoogleCheck());
            System.out.println("receiver 111111111111111111111" + p);
            privateService.savePrivate(p);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @RabbitListener(queues = "queueSignUp")
    public void receiveSignUp(User user) {
        try {
            Private p = new Private(user.getName(), user.getLastName(), user.getMail(), user.getPassword(), user.getPhoneNumber(), user.getGoogleCheck());
            System.out.println("receiver 111111111111111111111" + p);
            privateService.savePrivate(p);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
