package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ReservationDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> getRoomReservations(int roomNumber){
        return jdbcTemplate.queryForList("SELECT ID_REZERWACJA, NR_FAKTURA, NR_POKOJ, LOGIN, DATA_OD, DATA_DO, WALUTA, KWOTA_REZERWACJI FROM Rezerwacje WHERE NR_POKOJ=?", roomNumber);
    }
}
