package microservice.appointmentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.repo.AppointmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    @Override
    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    @Override
    @Transactional
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        return null;
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
        return new ResponseEntity<>("announcement has been deleted!", HttpStatus.OK);
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
}
