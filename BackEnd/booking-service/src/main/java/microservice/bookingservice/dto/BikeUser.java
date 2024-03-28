package microservice.bookingservice.dto;

import lombok.*;
import microservice.bookingservice.model.Booking;


@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeUser {
    private User user;
    private Bike bike;
    private Booking booking;
    private Adrent adrent;
}
