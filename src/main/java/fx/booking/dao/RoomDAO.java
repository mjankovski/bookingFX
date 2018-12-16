package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class RoomDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> getRoomsInfo(){
        return jdbcTemplate.queryForList("SELECT NR_POKOJ, LICZBA_OSOB, CENA FROM Pokoje");
    }

    public BigDecimal getRoomPrice(int roomNumber){
        return jdbcTemplate.queryForObject("SELECT CENA FROM Pokoje WHERE NR_POKOJ=?", BigDecimal.class, roomNumber);
    }
}
