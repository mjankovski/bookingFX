package fx.booking.dao;

import javafx.collections.ObservableList;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class Room {

    private int number;
    private BigDecimal dailyCost;
    private int peopleSize;
    private ObservableList<Reservation> reservationsList;

    public Room() {
    }

    public Room(int number, int peopleSize, BigDecimal dailyCost) {
        this.number = number;
        this.dailyCost = dailyCost;
        this.peopleSize = peopleSize;
    }
}
