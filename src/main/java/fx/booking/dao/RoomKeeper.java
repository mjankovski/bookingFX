package fx.booking.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class RoomKeeper {

    public ObservableList<Room> getRoomList() {
        //jak cos myslalem ze tu bedzie kontakt z baza danych i ona bedzie sciagala info o pokojach i ich cenach
        //na razie wpisuje dane o kilku pierwszych pokojach
        ObservableList<Reservation> reservationsList = FXCollections.observableArrayList();
        reservationsList.add(new Reservation(1, 101,"login",
                LocalDate.of(2012, 10, 01), LocalDate.of(2012,8,4),
                "pln", 1000));
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        roomList.add(new Room(101,1, 1000, 1, ReservationKeeper.getReservationList(101)));
        roomList.add(new Room(201,2, 2000, 2, ReservationKeeper.getReservationList(101)));
        //petla for each, z mapy bedzie sciagaly kolumny i ich wartosci
        for(int i = 0; i < 38; ++i) {
            roomList.add(new Room());
        }
        return roomList;
    }
}
