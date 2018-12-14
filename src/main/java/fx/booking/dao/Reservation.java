package fx.booking.dao;

import java.sql.Date;

public class Reservation {
    private int idReservation;
    private int invoiceNumber;
    private int roomNumber;
    private String login;
    private Date reservationBeginningDate;
    private Date reservationEndingDate;
    private String currency;
    private int reservationCost;

    public Reservation(int idReservation, int invoiceNumber, int roomNumber, String login, Date reservationBeginningDate, Date reservationEndingDate, String currency, int reservationCost) {
        this.idReservation = idReservation;
        this.invoiceNumber = invoiceNumber;
        this.roomNumber = roomNumber;
        this.login = login;
        this.reservationBeginningDate = reservationBeginningDate;
        this.reservationEndingDate = reservationEndingDate;
        this.currency = currency;
        this.reservationCost = reservationCost;
    }

    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Date getReservationBegginingDate() {
        return reservationBeginningDate;
    }

    public void setReservationBegginingDate(Date reservationBegginingDate) {
        this.reservationBeginningDate = reservationBegginingDate;
    }

    public Date getReservationEndingDate() {
        return reservationEndingDate;
    }

    public void setReservationEndingDate(Date reservationEndingDate) {
        this.reservationEndingDate = reservationEndingDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getReservationCost() {
        return reservationCost;
    }

    public void setReservationCost(int reservationCost) {
        this.reservationCost = reservationCost;
    }
}
