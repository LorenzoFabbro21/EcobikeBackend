package microservice.appointmentservice.model;


import java.util.List;

public class AppointmentList {
    private List<Appointment> appointments;

    public AppointmentList(List<Appointment> appointment) {
        this.appointments = appointment;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointment(List<Appointment> appointment) {
        this.appointments = appointment;
    }
}
