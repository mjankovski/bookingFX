package fx.booking.dao;

import javafx.collections.ObservableList;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Room {

    @Getter
    @Setter
    private int number;

    @Getter
    @Setter
    private int floor;

    @Getter
    @Setter
    private double dailyCost;

    private int peopleSize;

    @Getter
    @Setter
    private ObservableList<Reservation> reservationsList;

    public Room() {
    }

    public Room(int number, int floor, double dailyCost, int peopleSize, ObservableList<Reservation> reservationsList) {
        this.number = number;
        this.floor = floor;
        this.dailyCost = dailyCost;
        this.peopleSize = peopleSize;
        this.reservationsList = reservationsList;
    }
}
