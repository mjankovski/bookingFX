package fx.booking.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class ReservationKeeper {

    @Autowired
    private ReservationDAO reservationDAO;

    public ObservableList<Reservation> getReservationList(int roomNumber) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

        List<Map<String,Object>> rooms = reservationDAO.getRoomReservations(roomNumber);

        for (Map<String,Object> room: rooms) {
            reservationList.add(new Reservation((int)room.get("ID_REZERWACJA"), (int)room.get("NR_FAKTURA"), (int)room.get("NR_POKOJ"),
                    (String)room.get("LOGIN"), room.get("DATA_OD").toString(), room.get("DATA_DO").toString(),
                    (String)room.get("WALUTA"), (BigDecimal)room.get("KWOTA_REZERWACJI")));
        }

        return reservationList;
    }
}
