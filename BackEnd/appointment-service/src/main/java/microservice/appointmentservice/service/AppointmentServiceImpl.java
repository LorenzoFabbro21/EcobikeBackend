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
        if(appointment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        try {
            List<Appointment> appointments = new ArrayList<>();
            repository.findAll().forEach(appointments::add);
            return ResponseEntity.status(HttpStatus.OK).body(appointments);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Optional<Appointment>> getAppointmentById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Appointment> a = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(a);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAppointment(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Appointment has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteAllAppointments() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All appointments have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Appointment> updateAppointment(Long id, Appointment appointment) {
        if(id == null || appointment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<Appointment> AppointmentData = repository.findById(id);
            if (AppointmentData.isPresent()) {
                Appointment _appointment = AppointmentData.get();
                _appointment.setDate(appointment.getDate());
                repository.save(_appointment);
                return new ResponseEntity<>(repository.save(_appointment), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<Appointment>> getAllAppointmentsByUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<Appointment> appointments = repository.getAllAppointmentsByUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(appointments);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @Override
    public ResponseEntity<List<BikeUser>> getAllBikesSold(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<BikeUser> bikeUser = new ArrayList<>();
            List<AdSell> adsells = restTemplate.exchange(
                    "http://ad-service:8084/api/adsell/user/" + id,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<AdSell>>() {}
            ).getBody();

            //mettere null in tutte le get quando non si ritorna niente


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
            return ResponseEntity.status(HttpStatus.OK).body(bikeUser);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<List<BikeUser>> getPersonalBuy(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<BikeUser> bikeUser = new ArrayList<>();
            List<Appointment> listBookings = repository.getPersonalBuy(id);
            for (Appointment b : listBookings) {
                AdSell a = restTemplate.getForObject("http://ad-service:8084/api/adsell/" + b.getIdAnnouncement(), AdSell.class);
                Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + a.getIdBike(), Bike.class);
                User u = restTemplate.getForObject("http://user-service:8081/api/private/" + b.getIdUser(), User.class);
                BikeUser obj = new BikeUser(u, bike, b, a);
                bikeUser.add(obj);
            }
            return ResponseEntity.status(HttpStatus.OK).body(bikeUser);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}