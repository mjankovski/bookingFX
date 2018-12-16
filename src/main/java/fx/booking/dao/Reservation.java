package fx.booking.dao;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int invoiceNumber;
    private int roomNumber;
    private String login;
    private LocalDate beginningDate;
    private LocalDate endingDate;
    private String currency;
    private double cost;
    private static int reservationNumber;

    public Reservation() {
    }

    public Reservation(int invoiceNumber, int roomNumber, String login, LocalDate beginningDate, LocalDate endingDate, String currency, double cost) {
        this.id = reservationNumber++;
        this.invoiceNumber = invoiceNumber;
        this.roomNumber = roomNumber;
        this.login = login;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.currency = currency;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public LocalDate getBeginningDate() {
        return beginningDate;
    }

    public void setBeginningDate(LocalDate beginningDate) {
        this.beginningDate = beginningDate;
    }

    public LocalDate getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        this.endingDate = endingDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
