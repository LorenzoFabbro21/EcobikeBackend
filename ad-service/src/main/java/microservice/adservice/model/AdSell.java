package microservice.adservice.model;

import javax.persistence.Entity;

@Entity
public class AdSell extends  Ad {

    public AdSell() {
        super();
    }

    public AdSell(float price, long idBike) {
        super(price, idBike);
    }
}
