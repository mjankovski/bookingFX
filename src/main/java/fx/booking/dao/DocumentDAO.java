package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

@Repository
public class DocumentDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createDocument(BigDecimal cost){
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement("INSERT INTO Faktury (DATA_WYSTAWIENIA, KWOTA_FAKTURY) VALUES (?, ?)",
                    new String[] {"NR_FAKTURA"});
            ps.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
            ps.setBigDecimal(2, cost);
            return ps;
        }, key);
        return (int) Objects.requireNonNull(key.getKey()).longValue();
    }

    public Map<String, Object> getDocumentsInformation(int docNumber){
        return jdbcTemplate.queryForMap("SELECT U.IMIE, U.NAZWISKO, U.EMAIL, U.NR_TEL, R.ID_REZERWACJA, R.WALUTA, F.NR_FAKTURA, F.KWOTA_FAKTURY, F.DATA_WYSTAWIENIA FROM Faktury F INNER JOIN Rezerwacje R ON F.NR_FAKTURA=R.NR_FAKTURA INNER JOIN Uzytkownicy U ON R.LOGIN=U.LOGIN WHERE R.ID_REZERWACJA=?", docNumber);
    }

}