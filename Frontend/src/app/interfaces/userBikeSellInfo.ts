import { User } from "../classes/user";
import { adRent } from "./adRent";
import { adSell } from "./adSell";
import { Appointment } from "./appointment";
import { Bicicletta } from "./bicicletta";
import { Booking } from "./booking";

export interface userBikeSellInfo {
    user: User;
    bike: Bicicletta;
    appointment: Appointment | Booking;
    adsell: adSell;
  
  }