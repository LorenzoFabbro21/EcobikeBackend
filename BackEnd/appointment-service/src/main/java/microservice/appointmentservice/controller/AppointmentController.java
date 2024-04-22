package microservice.appointmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping(value = "")
    public ResponseEntity<?> postAppointment(@RequestBody Appointment appointment) {
        return  appointmentService.saveAppointment(new Appointment(appointment.getId(), appointment.getIdUser(), appointment.getIdAnnouncement(), appointment.getDate()));
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
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") long id) {
        System.out.println("Delete Appointment with ID = " + id + "...");
        return appointmentService.deleteAppointment(id);

    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAppointments() {
        System.out.println("Delete all Appointments...");
        return appointmentService.deleteAllAppointments();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
        System.out.println("Update Appointment with ID = " + id + "...");
        return appointmentService.updateAppointment(id,appointment);

    }

    @GetMapping("/user/{idUser}")
    public List<Appointment> getAllAppointmentByUser(@PathVariable("idUser") long id) {
        System.out.println("Get all Appointments by idUser...");
        return appointmentService.getAllAppointmentsByUser(id);
    }

    @GetMapping("/user/{idUser}/bikes")
    public List<BikeUser> getAllBikesSold(@PathVariable("idUser") long id) {
        System.out.println("Get all Bikes sold by idUser...");
        return appointmentService.getAllBikesSold(id);
    }

    @GetMapping("/personal/user/{idPrivate}/bikes")
    public List<BikeUser> getPersonalBuy(@PathVariable("idPrivate") long id) {
        System.out.println("Get personal buy...");
        List<BikeUser> list = appointmentService.getPersonalBuy(id);
        System.out.println(list);
        return list;
    }

}
