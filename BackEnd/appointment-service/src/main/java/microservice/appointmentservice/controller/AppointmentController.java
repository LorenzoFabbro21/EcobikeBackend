package microservice.appointmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.service.AppointmentService;
import org.springframework.http.HttpStatus;
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
        if(appointment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            return  appointmentService.saveAppointment(new Appointment(appointment.getId(), appointment.getIdUser(), appointment.getIdAnnouncement(), appointment.getDate()));
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Appointment>> getAppointment(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get Appointment...");
            return appointmentService.getAppointmentById(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Appointment>> getAllAppointment() {
        try {
            System.out.println("Get all Appointments...");
            return appointmentService.getAllAppointments();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Delete Appointment with ID = " + id + "...");
            return appointmentService.deleteAppointment(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAppointments() {
        try {
            System.out.println("Delete all Appointments...");
            return appointmentService.deleteAllAppointments();
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") Long id, @RequestBody Appointment appointment) {
        if(id == null || appointment == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Update Appointment with ID = " + id + "...");
            return appointmentService.updateAppointment(id,appointment);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{idUser}")
    public ResponseEntity<List<Appointment>> getAllAppointmentByUser(@PathVariable("idUser") Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            System.out.println("Get all Appointments by idUser...");
            return appointmentService.getAllAppointmentsByUser(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/user/{idUser}/bikes")
    public ResponseEntity<List<BikeUser>> getAllBikesSold(@PathVariable("idUser") Long id) {
        try {
            System.out.println("Get all Bikes sold by idUser...");
            return appointmentService.getAllBikesSold(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/personal/user/{idPrivate}/bikes")
    public ResponseEntity<List<BikeUser>> getPersonalBuy(@PathVariable("idPrivate") Long id) {
        try {
            System.out.println("Get personal buy...");
            return appointmentService.getPersonalBuy(id);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to post ad rent");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}