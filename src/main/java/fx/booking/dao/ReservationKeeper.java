package fx.booking.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ReservationKeeper {
    private ReservationKeeper() {};

    //sciaga rezerwacje z bazy danych dla pokoju o danym numerze
    public static ObservableList<Reservation> getReservationList(int roomNumber) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        reservationList.add(new Reservation(1, 101,"login",
                LocalDate.of(2012, 10, 01),
                LocalDate.of(2012,8,4),
                "pln", 1000));
        return reservationList;
    }
}
