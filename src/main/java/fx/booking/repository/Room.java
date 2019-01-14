package fx.booking.repository;

import javafx.collections.ObservableList;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Room {

    private int number;
    private BigDecimal dailyCost;
    private int peopleSize;
    private ObservableList<Reservation> reservationsList;

    Room(int number, int peopleSize, BigDecimal dailyCost) {
        this.number = number;
        this.dailyCost = dailyCost;
        this.peopleSize = peopleSize;
    }
}
