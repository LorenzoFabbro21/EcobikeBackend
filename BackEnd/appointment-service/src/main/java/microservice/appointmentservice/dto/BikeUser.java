package microservice.appointmentservice.dto;

import lombok.*;
import microservice.appointmentservice.model.Appointment;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BikeUser {
    private User user;
    private Bike bike;
    private Appointment appointment;
    private AdSell adsell;
}
