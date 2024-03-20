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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;

    @Autowired
    private RestTemplate restTemplate;
    @Override
    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
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
    public ResponseEntity<String> deleteAppointment(long id) {
        repository.deleteById(id);
        return new ResponseEntity<>("Appointment has been deleted!", HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteAllAppointments() {
        repository.deleteAll();
        return new ResponseEntity<>("All announcement have been deleted!", HttpStatus.OK);
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
                "http://ad-service/api/adsell/user/" + id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AdSell>>() {}
        ).getBody();
        for (AdSell a: adsells) {
            List<Appointment> appointments = repository.getAllAppointmentsByAnnouncement(a.getId());
            Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + a.getIdBike(), Bike.class);
            for(Appointment b: appointments){
                Optional<User> userprivate = Optional.ofNullable(restTemplate.getForObject("http://user-service/api/private/" + b.getIdUser(), User.class));
                if(userprivate.isPresent()) {
                    BikeUser obj = new BikeUser(userprivate.get(), bike);
                    bikeUser.add(obj);
                }
                else {
                    Optional<User> dealer = Optional.ofNullable(restTemplate.getForObject("http://user-service/api/dealer/" + b.getIdUser(), User.class));
                    BikeUser obj = new BikeUser(dealer.get(), bike);
                    bikeUser.add(obj);
                }

            }
        }
        return bikeUser;
    }
}
