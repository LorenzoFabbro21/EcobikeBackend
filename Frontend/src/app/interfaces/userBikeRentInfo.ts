import { User } from "../classes/user";
import { adRent } from "./adRent";
import { Bicicletta } from "./bicicletta";
import { Booking } from "./booking";

export interface userBikeRentInfo {
    user: User;
    bike: Bicicletta;
    booking: Booking;
    adrent: adRent;
  
  }