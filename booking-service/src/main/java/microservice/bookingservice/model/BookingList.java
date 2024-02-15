package microservice.bookingservice.model;


import java.util.List;

public class BookingList {
    private List<Booking> booking;

    public BookingList(List<Booking> booking) {
        this.booking = booking;
    }

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }
}
