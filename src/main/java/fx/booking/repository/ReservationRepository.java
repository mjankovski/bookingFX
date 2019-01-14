package fx.booking.repository;

import fx.booking.dao.ReservationDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationRepository {

    @Autowired
    private ReservationDAO reservationDAO;

    public ObservableList<Reservation> getReservationList(int roomNumber) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        List<Map<String,Object>> rooms = reservationDAO.getRoomReservations(roomNumber);

        addReservationsToList(rooms, reservationList);

        return reservationList;
    }

    public ObservableList<Reservation> getReservationList(String login) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        List<Map<String,Object>> rooms = reservationDAO.getAccountReservations(login);

        addReservationsToList(rooms, reservationList);

        return reservationList;
    }

    private void addReservationsToList(List<Map<String,Object>> rooms, ObservableList<Reservation> reservations){
        for (Map<String,Object> room: rooms) {
            reservations.add(new Reservation((int)room.get("ID_REZERWACJA"), (int)room.get("NR_FAKTURA"), (int)room.get("NR_POKOJ"),
                    (String)room.get("LOGIN"), room.get("DATA_OD").toString(), room.get("DATA_DO").toString(),
                    (String)room.get("WALUTA"), (BigDecimal)room.get("KWOTA_REZERWACJI")));
        }
    }
}
