package microservice.userservice.service;

import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface PrivateService {

        ResponseEntity<String> savePrivate(Private user);

        List<Private> getAllPrivates();

        Optional<Private> getPrivateById(long id);

        ResponseEntity<String> deletePrivate(long id);

        ResponseEntity<String> deleteAllPrivates();

        ResponseEntity<Private> updatePrivate(long id, @RequestBody Private userprivate);

        Optional<Private> getPrivateByMail(String mail);

        ResponseEntity<String> verifyParams(String email, String password);
}
