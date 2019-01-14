package fx.booking.repository;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Reservation {
    private int id;
    private int invoiceNumber;
    private int roomNumber;
    private String login;
    private String beginningDate;
    private String endingDate;
    private String currency;
    private BigDecimal cost;

    Reservation(int id, int invoiceNumber, int roomNumber, String login, String beginningDate, String endingDate, String currency, BigDecimal cost) {
        this.id = id;
        this.invoiceNumber = invoiceNumber;
        this.roomNumber = roomNumber;
        this.login = login;
        this.beginningDate = beginningDate;
        this.endingDate = endingDate;
        this.currency = currency;
        this.cost = cost;
    }
}
