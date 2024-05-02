package microservice.reviewservice.dto;

import lombok.*;
import microservice.reviewservice.model.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUserResponse {
    private Review review;
    private User user;
}
