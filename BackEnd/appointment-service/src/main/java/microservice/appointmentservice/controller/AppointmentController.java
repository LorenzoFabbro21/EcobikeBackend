package microservice.appointmentservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import microservice.appointmentservice.dto.BikeUser;
import microservice.appointmentservice.model.Appointment;
import microservice.appointmentservice.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
@Slf4j
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid order request")
    })
    @PostMapping(value = "")
    public ResponseEntity<?> postAppointment(@RequestBody Appointment appointment) {
        return  appointmentService.saveAppointment(new Appointment(appointment.getId(), appointment.getIdUser(), appointment.getIdAnnouncement(), appointment.getDate()));
    }

    @Operation(summary = "Get an appointment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @GetMapping("/{id}")
    public Optional<Appointment> getAppointment(@PathVariable("id") long id) {
        System.out.println("Get Appointment...");
        Optional<Appointment> appointment;
        appointment = appointmentService.getAppointmentById(id);
        return appointment;
    }
    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointments not found")
    })
    @GetMapping("")
    public List<Appointment> getAllAppointment() {
        System.out.println("Get all Appointments...");
        List<Appointment> appointments = new ArrayList<>();
        appointmentService.getAllAppointments().forEach(appointments::add);
        return appointments;
    }

    @Operation(summary = "Delete an appointment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable("id") long id) {
        System.out.println("Delete Appointment with ID = " + id + "...");
        return appointmentService.deleteAppointment(id);

    }
    @Operation(summary = "Delete all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointments not found")
    })
    @DeleteMapping("")
    public ResponseEntity<?> deleteAllAppointments() {
        System.out.println("Delete all Appointments...");
        return appointmentService.deleteAllAppointments();

    }

    @Operation(summary = "Update an appointment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable("id") long id, @RequestBody Appointment appointment) {
        System.out.println("Update Appointment with ID = " + id + "...");
        return appointmentService.updateAppointment(id,appointment);

    }

    @Operation(summary = "Get all appointments by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Appointments not found")
    })
    @GetMapping("/user/{idUser}")
    public List<Appointment> getAllAppointmentByUser(@PathVariable("idUser") long id) {
        System.out.println("Get all Appointments by idUser...");
        return appointmentService.getAllAppointmentsByUser(id);
    }

    @Operation(summary = "Get all bikes sold by idUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bikes sold found"),
            @ApiResponse(responseCode = "400", description = "Invalid order request"),
            @ApiResponse(responseCode = "404", description = "Bikes sold not found")
    })
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
