package fx.booking.repository;

import fx.booking.dao.RoomDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class RoomKeeper {

    @Autowired
    private RoomDAO roomDAO;

    public ObservableList<Room> getRoomList() {
        ObservableList<Room> roomList = FXCollections.observableArrayList();

        List<Map<String,Object>> rooms = roomDAO.getRoomsInfo();

        for (Map<String,Object> room: rooms) {
            roomList.add(new Room((int)room.get("NR_POKOJ"),(int)room.get("LICZBA_OSOB"), (BigDecimal)room.get("CENA")));
        }

        return roomList;
    }
}
