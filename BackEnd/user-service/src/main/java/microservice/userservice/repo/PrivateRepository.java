package microservice.userservice.repo;

import microservice.userservice.model.Private;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface PrivateRepository extends JpaRepository<Private, Long> {
    Optional<Private> findByMail(String email);

}