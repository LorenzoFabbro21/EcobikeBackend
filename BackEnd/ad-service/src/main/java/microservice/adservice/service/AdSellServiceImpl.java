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
        try {
            AdSell adSellCreated = repository.save(adSell);
            Map<String, String> body = new HashMap<>();
            body.put("messageResponse", "Sell successfully created");
            body.put("id", String.valueOf(adSellCreated.getId()));
            return ResponseEntity.status(HttpStatus.OK).body(body);
        } catch (Exception e) {

            Map<String, String> errorBody = new HashMap<>();
            errorBody.put("error", "Failed to create bike");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
    @Override
    public List<AdSell> getAllAdsSell() {
        List<AdSell> adsSell = new ArrayList<>();
        repository.findAll().forEach(adsSell::add);
        return adsSell;
    }
    @Override
    public List<AdSell> getAllAdsSellNotSold() {

        //Get di tutti gli appointment
        ResponseEntity<List<Appointment>> response = restTemplate.exchange(
                "http://appointment-service/api/appointment",
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

        return filteredAdSell;
    }

    @Override
    public List<AdSell> getAllAdsSellForUser(long id) {

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

        return filteredAdSell;



        //List<AdSell> adsSell = new ArrayList<>();
        //repository.getAllAdSellForUser(id).forEach(adsSell::add);
        //return adsSell;
    }

    @Override
    public Optional<AdSell> getAdSellById(long id) {
        return repository.findById(id);
    }

    @Override
    public ResponseEntity<?> deleteAdSell(long id) {
        repository.deleteById(id);
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "AdSell has been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<?> deleteAllAdsSell() {
        repository.deleteAll();
        Map<String, String> body = new HashMap<>();
        body.put("messageResponse", "All adsSell have been deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Override
    public ResponseEntity<AdSell> updateAdSell(long id, AdSell adSell) {
        Optional<AdSell> adSellData = repository.findById(id);

        if (adSellData.isPresent()) {
            AdSell AdSell = adSellData.get();
            AdSell.setPrice(adSell.getPrice());
            AdSell.setIdBike(adSell.getIdBike());
            repository.save(AdSell);
            return new ResponseEntity<>(repository.save(AdSell), HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public List<Bike> getBikesToSell() {
        List<AdSell> adsSell = new ArrayList<>();
        adsSell = this.getAllAdsSell();
        if (!adsSell.isEmpty()) {
            List<Bike> bikes = new ArrayList<>();
            for (AdSell elem : adsSell) {
                Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + elem.getIdBike(), Bike.class);
                bikes.add(bike);
            }
            return bikes;
        } else {
            return null;
        }
    }

    public List<AdSell> getAllAdSellByUser(long id){
        return repository.getAllAdSellByUser(id);
    }
    @Override
    public List<Bike> getAllBikeToSellByUser(long id) {
        List<AdSell> adSells = new ArrayList<>();
        adSells= this.getAllAdSellByUser(id);

        if ( !adSells.isEmpty())
        {
            List<Bike> bikes = new ArrayList<>();
            for ( AdSell elem : adSells) {
                Bike bike = restTemplate.getForObject("http://bike-service/api/bike/" + elem.getIdBike(), Bike.class);
                bikes.add(bike);
            }
            return bikes;
        }
        else {
            return null;
        }
    }

}