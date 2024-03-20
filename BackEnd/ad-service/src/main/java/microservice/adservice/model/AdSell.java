package microservice.adservice.model;


import jakarta.persistence.*;

@Entity
public class AdSell extends  Ad {

    public AdSell() {
        super();
    }

    public AdSell(float price, long idBike, long idUser) {
        super(price, idBike, idUser);
    }
}
