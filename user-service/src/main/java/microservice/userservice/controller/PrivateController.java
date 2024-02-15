package microservice.userservice.controller;

import lombok.*;
import lombok.extern.slf4j.*;
import microservice.userservice.model.Dealer;
import microservice.userservice.model.Private;
import microservice.userservice.service.DealerService;
import microservice.userservice.service.PrivateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/private")
@RequiredArgsConstructor
@Slf4j
public class PrivateController {

    private final PrivateService privateService;

    @PostMapping(value = "")
    public Private postPrivate(@RequestBody Private user) {

        Private _Private = privateService.savePrivate(new Private(user.getNome(), user.getCognome(), user.getMail(), user.getPassword(), user.getTelefono()));
        return _Private;
    }

    @GetMapping("/{id}")
    public Optional<Private> getPrivate(@PathVariable("id") long id) {
        System.out.println("Get Private...");
        Optional<Private> userprivate = Optional.of(new Private());
        userprivate = privateService.getPrivateById(id);
        return userprivate;
    }

    @GetMapping("")
    public List<Private> getAllPrivate() {
        System.out.println("Get all Privates...");
        List<Private> userprivate = new ArrayList<>();
        userprivate= privateService.getAllPrivates();
        return userprivate;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrivate(@PathVariable("id") long id) {
        System.out.println("Delete Private with ID = " + id + "...");
        ResponseEntity<String> response = privateService.deletePrivate(id);
        return response;
    }
    @DeleteMapping("")
    public ResponseEntity<String> deleteAllPrivates() {
        System.out.println("Delete All Privates...");

        ResponseEntity<String> response = privateService.deleteAllPrivates();

        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Private> updatePrivates(@PathVariable("id") long id, @RequestBody Private userprivate) {
        System.out.println("Update Dealer with ID = " + id + "...");
        ResponseEntity<Private> response = privateService.updatePrivate(id, userprivate);
        return response;
    }
}
