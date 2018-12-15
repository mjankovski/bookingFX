package fx.booking.dao;

import java.sql.Date;
import java.time.LocalDate;

public class Reservation {
    private int idReservation;
    private int invoiceNumber;
    private int roomNumber;
    private String login;
    private LocalDate reservationBeginningDate;
    private LocalDate reservationEndingDate;
    private String currency;
    private int reservationCost;
    private static int reservationNumber;

    public Reservation(int invoiceNumber, int roomNumber, String login, LocalDate reservationBeginningDate, LocalDate reservationEndingDate, String currency, int reservationCost) {
        this.idReservation = reservationNumber++;
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

    public LocalDate getReservationBeginningDate() {
        return reservationBeginningDate;
    }

    public void setReservationBeginningDate(LocalDate reservationBeginningDate) {
        this.reservationBeginningDate = reservationBeginningDate;
    }

    public LocalDate getReservationEndingDate() {
        return reservationEndingDate;
    }

    public void setReservationEndingDate(LocalDate reservationEndingDate) {
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
