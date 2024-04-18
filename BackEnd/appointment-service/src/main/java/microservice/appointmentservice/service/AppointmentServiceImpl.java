package microservice.appointmentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.dto.AdSell;
import microservice.appointmentservice.dto.Bike;
import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.dto.User;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.repo.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    @Transactional
    public ResponseEntity<?> saveAppointment(Appointment appointment) {
        try {
            Appointment appointmentCreated = repository.save(appointment);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Appointment successfully created");
            body.put("id", String.valueOf(appointmentCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {

            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

    }

    @Override
    @Transactional
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        repository.findAll().forEach(appointments::add);
        return appointments;
    }

    @Override
    @Transactional
    public Optional<Appointment> getAppointmentById(long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAppointment(long id) {
        repository.deleteById(id);
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "Appointment has been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAllAppointments() {
        repository.deleteAll();
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "All appointments have been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    @Transactional
    public ResponseEntity<Appointment> updateAppointment(long id, Appointment appointment) {
        Optional<Appointment> AppointmentData = repository.findById(id);

        if (AppointmentData.isPresent()) {
            Appointment _appointment = AppointmentData.get();
            _appointment.setDate(appointment.getDate());
            repository.save(_appointment);
            return new ResponseEntity<>(repository.save(_appointment), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public List<Appointment> getAllAppointmentsByUser(long id) {
        List<Appointment> appointments = repository.getAllAppointmentsByUser(id);
        return appointments;
    }



    @Override
    public List<BikeUser> getAllBikesSold(long id) {
        List<BikeUser> bikeUser = new ArrayList<>();
        List<AdSell> adsells = restTemplate.exchange(
                "http://ad-service:8084/api/adsell/user/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdSell>>() {}
        ).getBody();
        for (AdSell a: adsells) {
            List<Appointment> appointments = repository.getAllAppointmentsByAnnouncement(a.getId());
            Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + a.getIdBike(), Bike.class);
            for(Appointment b: appointments){
                Optional<User> userprivate = Optional.ofNullable(restTemplate.getForObject("http://user-service/api/private/" + b.getIdUser(), User.class));
                if(userprivate.isPresent()) {
                    BikeUser obj = new BikeUser(userprivate.get(), bike, b, a);
                    bikeUser.add(obj);
                }
                else {
                    Optional<User> dealer = Optional.ofNullable(restTemplate.getForObject("http://user-service:8081/api/dealer/" + b.getIdUser(), User.class));
                    BikeUser obj = new BikeUser(dealer.get(), bike, b, a);
                    bikeUser.add(obj);
                }

            }
        }
        return bikeUser;
    }


    @Override
    @Transactional
    public List<BikeUser> getPersonalBuy(long id) {
        List<BikeUser> bikeUser = new ArrayList<>();
        List<Appointment> listBookings = repository.getPersonalBuy(id);
        for (Appointment b : listBookings) {
            AdSell a = restTemplate.getForObject("http://ad-service:8084/api/adsell/" + b.getIdAnnouncement(), AdSell.class);
            Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + a.getIdBike(), Bike.class);
            User u = restTemplate.getForObject("http://user-service:8081/api/private/" + b.getIdUser(), User.class);
            BikeUser obj = new BikeUser(u, bike, b, a);
            bikeUser.add(obj);
        }

        return bikeUser;
    }


}
