package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Repository
public class ReservationDAO {

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private AccountDAO accountDAO;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> getRoomReservations(int roomNumber){
        return jdbcTemplate.queryForList("SELECT ID_REZERWACJA, NR_FAKTURA, NR_POKOJ, LOGIN, DATA_OD, DATA_DO, WALUTA, KWOTA_REZERWACJI FROM Rezerwacje WHERE NR_POKOJ=? ORDER BY ID_REZERWACJA ASC", roomNumber);
    }

    public List<Map<String,Object>> getAccountReservations(String login){
        return jdbcTemplate.queryForList("SELECT ID_REZERWACJA, NR_FAKTURA, NR_POKOJ, LOGIN, DATA_OD, DATA_DO, WALUTA, KWOTA_REZERWACJI FROM Rezerwacje WHERE LOGIN = ? ORDER BY ID_REZERWACJA ASC", login);
    }

    public Boolean checkIfRoomFree(int roomNumber, String fromDate, String toDate){
        int count = jdbcTemplate.queryForObject("SELECT COUNT(ID_REZERWACJA) FROM Rezerwacje WHERE NR_POKOJ=? AND ((DATA_OD>=? AND DATA_OD<?) OR (DATA_OD<=? AND DATA_DO>?) OR (DATA_OD>=? AND DATA_DO<=?) OR (DATA_OD<=? AND DATA_DO>?))", Integer.class, roomNumber, fromDate, toDate, fromDate, fromDate, toDate, toDate, fromDate, toDate);
        if(count>0) return false;
        return true;
    }

    public void insertReservation(int roomNumber, String fromDate, String toDate, BigDecimal dailyCost, String currency){
        final LocalDate fromDateFormatted = LocalDate.parse(fromDate);
        final LocalDate toDateFormatted = LocalDate.parse(toDate);
        LocalDate today = LocalDate.now();

        if(fromDateFormatted.compareTo(toDateFormatted)>=0 || fromDateFormatted.compareTo(today)<0) throw new IllegalArgumentException();

        if(checkIfRoomFree(roomNumber,fromDate,toDate)) {
            long days = DAYS.between(fromDateFormatted,toDateFormatted);
            BigDecimal cost = dailyCost.multiply(new BigDecimal(days));
            int docId = documentDAO.createDocument(cost);

            jdbcTemplate.update(
                    "INSERT INTO Rezerwacje (NR_FAKTURA,NR_POKOJ,LOGIN,DATA_OD,DATA_DO,WALUTA, KWOTA_REZERWACJI) VALUES (?, ?, ?, ?, ?, ? ,? )",
                    docId, roomNumber, accountDAO.getLogin(), fromDate, toDate, currency, cost
            );
        }
    }

    public void deleteReservation(int reservationId){
        jdbcTemplate.update("DELETE FROM Faktury WHERE NR_FAKTURA IN (SELECT NR_FAKTURA FROM Rezerwacje WHERE ID_REZERWACJA = ?)", reservationId);
        jdbcTemplate.update("DELETE FROM Rezerwacje WHERE ID_REZERWACJA = ?", reservationId);
    }
}
