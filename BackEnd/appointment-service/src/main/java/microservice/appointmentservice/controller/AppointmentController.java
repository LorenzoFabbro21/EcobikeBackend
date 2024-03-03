package microservice.appointmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping(value = "")
    public Appointment postAppointment(@RequestBody Appointment appointment) {
        Appointment _appointment = appointmentService.saveAppointment(new Appointment(appointment.getId(), appointment.getIdUser(), appointment.getIdAnnouncement(), appointment.getDate()));
        return _appointment;
    }

    @GetMapping("/{id}")
    public Optional<Appointment> getAppointment(@PathVariable("id") long id) {
        System.out.println("Get Appointment...");
        Optional<Appointment> appointment;
        appointment = appointmentService.getAppointmentById(id);
        return appointment;
    }

    @GetMapping("")
    public List<Appointment> getAllAppointment() {
        System.out.println("Get all Appointments...");
        List<Appointment> appointments = new ArrayList<>();
        appointmentService.getAllAppointments().forEach(appointments::add);
        return appointments;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(@PathVariable("id") long id) {
        System.out.println("Delete Appointment with ID = " + id + "...");
        return appointmentService.deleteAppointment(id);

    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllAppointments() {
        System.out.println("Delete all Appointments...");
        return appointmentService.deleteAllAppointments();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
        System.out.println("Update Appointment with ID = " + id + "...");
        return appointmentService.updateAppointment(id,appointment);

    }
}
