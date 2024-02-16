package microservice.recensioneservice.dto;

import lombok.*;
import microservice.recensioneservice.model.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUserResponse {
    private Recensione review;
    private User user;
}
