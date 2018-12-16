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
    private RoomDAO roomDAO;

    @Autowired
    private AccountDAO accountDAO;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> getRoomReservations(int roomNumber){
        return jdbcTemplate.queryForList("SELECT ID_REZERWACJA, NR_FAKTURA, NR_POKOJ, LOGIN, DATA_OD, DATA_DO, WALUTA, KWOTA_REZERWACJI FROM Rezerwacje WHERE NR_POKOJ=?", roomNumber);
    }

    public Boolean checkIfRoomFree(int roomNumber, String fromDate, String toDate){
        int count = jdbcTemplate.queryForObject("SELECT COUNT(ID_REZERWACJA) FROM Rezerwacje WHERE NR_POKOJ=? AND ((DATA_OD<? AND DATA_DO>?) OR (DATA_OD<? AND DATA_DO>?) OR (DATA_OD>? AND DATA_DO<?))", Integer.class, roomNumber, fromDate, fromDate, toDate, toDate, fromDate, toDate);
        if(count>0) return false;
        return true;
    }

    public boolean insertReservation(int roomNumber, String fromDate, String toDate){
        final LocalDate fromDateFormatted = LocalDate.parse(fromDate);
        final LocalDate toDateFormatted = LocalDate.parse(toDate);

        //if(fromDateFormatted.compareTo(toDateFormatted)>0) rzuc wyjatek;
        //jeszcze upewnic sie ze data_od jest po dzisiaj

        long days = DAYS.between(fromDateFormatted,toDateFormatted);
        BigDecimal cost = roomDAO.getRoomPrice(roomNumber).multiply(new BigDecimal(days));
        System.out.println(accountDAO.getLogin());
        int docId = documentDAO.createDocument(cost);

        if(checkIfRoomFree(roomNumber,fromDate,toDate)) {
            jdbcTemplate.update(
                    "INSERT INTO Rezerwacje (NR_FAKTURA,NR_POKOJ,LOGIN,DATA_OD,DATA_DO,WALUTA, KWOTA_REZERWACJI) VALUES (?, ?, ?, ?, ?, ? ,? )",
                    docId, roomNumber, accountDAO.getLogin(), fromDate, toDate, "PLN", cost
            );
            return true;
        }
        return false;
    }
}
