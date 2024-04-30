package microservice.adservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import microservice.adservice.dto.*;
import microservice.adservice.model.*;
import microservice.adservice.repo.*;
import org.hibernate.dialect.identity.HSQLIdentityColumnSupport;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdSellServiceImpl implements AdSellService {

    private final AdSellRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<?> saveAdSell(AdSell adSell) {
        if(adSell == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            String response = validateRequest(adSell);
            if (!response.equals("ok")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            AdSell adSellCreated = repository.save(adSell);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Sell successfully created");
            body.put("id", String.valueOf(adSellCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }


    private String validateRequest(AdSell adSell) {
        String response = validateAdSellParams(adSell);
        if (!response.equals("ok"))
            return response;
        return "ok";
    }

    private String validateAdSellParams(AdSell adSell) {
        if (adSell.getPrice() <= 0)
            return "Prezzo inserito non valido";
        return "ok";
    }

    @Override
    public ResponseEntity<List<AdSell>> getAllAdsSell() {
        try {
            List<AdSell> adsSell = new ArrayList<>();
            repository.findAll().forEach(adsSell::add);
            return ResponseEntity.status(HttpStatus.OK).body(adsSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Override
    public ResponseEntity<List<AdSell>> getAllAdsSellNotSold() {
        try {
            //Get di tutti gli appointment
            ResponseEntity<List<Appointment>> response = restTemplate.exchange(
                    "http://appointment-service:8086/api/appointment",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Appointment>>() {}
            );
            List<Appointment> appointments = response.getBody();
            //Get degli annunci di vendita
            List<AdSell> adsSell = new ArrayList<>();
            repository.findAll().forEach(adsSell::add);

            //Crea lista con gli idAnnouncement di tutti gli appointment
            List<Long> appointmentAdSellIds = appointments.stream()
                    .map(Appointment::getIdAnnouncement)
                    .collect(Collectors.toList());

            //Filtra gli AdSell che hanno ID diversi da quelli presenti negli appuntamenti
            List<AdSell> filteredAdSell = adsSell.stream()
                    .filter(adSell -> !appointmentAdSellIds.contains(adSell.getId()))
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(filteredAdSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads sell notsold");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<AdSell>> getAllAdsSellForUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            ResponseEntity<List<Appointment>> response = restTemplate.exchange(
                    "http://appointment-service/api/appointment",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Appointment>>() {}
            );
            List<Appointment> appointments = response.getBody();
            //Get degli annunci di vendita
            List<AdSell> adsSell = new ArrayList<>();
            repository.getAllAdSellForUser(id).forEach(adsSell::add);

            //Crea lista con gli idAnnouncement di tutti gli appointment
            List<Long> appointmentAdSellIds = appointments.stream()
                    .map(Appointment::getIdAnnouncement)
                    .collect(Collectors.toList());

            //Filtra gli AdSell che hanno ID diversi da quelli presenti negli appuntamenti
            List<AdSell> filteredAdSell = adsSell.stream()
                    .filter(adSell -> !appointmentAdSellIds.contains(adSell.getId()))
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK).body(filteredAdSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain all ads sell for user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Optional<AdSell>> getAdSellById(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<AdSell> ad = repository.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(ad);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain ad sell by id");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<?> deleteAdSell(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            repository.deleteById(id);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "AdSell has been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public ResponseEntity<?> deleteAllAdsSell() {
        try {
            repository.deleteAll();
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "All adsSell have been deleted!");
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to delete all ads sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    @Override
    public ResponseEntity<AdSell> updateAdSell(Long id, AdSell adSell) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            Optional<AdSell> adSellData = repository.findById(id);
            if (adSellData.isPresent()) {
                AdSell AdSell = adSellData.get();
                AdSell.setPrice(adSell.getPrice());
                AdSell.setIdBike(adSell.getIdBike());
                repository.save(AdSell);
                return new ResponseEntity<>(repository.save(AdSell), HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to update ad sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @Override
    public ResponseEntity<List<Bike>> getBikesToSell() {
        try {
            ResponseEntity<List<AdSell>> adsSell = this.getAllAdsSell();
            List<AdSell> l = adsSell.getBody();
            if (!l.isEmpty()) {
                List<Bike> bikes = new ArrayList<>();
                for (AdSell elem : l) {
                    Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + elem.getIdBike(), Bike.class);
                    bikes.add(bike);
                }
                return ResponseEntity.status(HttpStatus.OK).body(bikes);
            } else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bike to sell");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }



    }

    public ResponseEntity<List<AdSell>> getAllAdSellByUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            List<AdSell> adsSell = new ArrayList<>();
            repository.getAllAdSellByUser(id).forEach(adsSell::add);
            return ResponseEntity.status(HttpStatus.OK).body(adsSell);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain sell by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @Override
    public ResponseEntity<List<Bike>> getAllBikeToSellByUser(Long id) {
        if(id == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        try {
            ResponseEntity<List<AdSell>> adsSell = this.getAllAdSellByUser(id);
            List<AdSell> l = adsSell.getBody();
            if ( !l.isEmpty()) {
                List<Bike> bikes = new ArrayList<>();
                for ( AdSell elem : l) {
                    Bike bike = restTemplate.getForObject("http://bike-service:8087/api/bike/" + elem.getIdBike(), Bike.class);
                    bikes.add(bike);
                }
                return ResponseEntity.status(HttpStatus.OK).body(bikes);
            }
            else
                return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to obtain bike to sell by user");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}