package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Calendar;

@Repository
public class DocumentDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createDocument(BigDecimal cost){
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    final PreparedStatement ps = connection.prepareStatement("INSERT INTO Faktury (DATA_WYSTAWIENIA, KWOTA_FAKTURY) VALUES (?, ?)",
                            new String[] {"NR_FAKTURA"});
                    ps.setTimestamp(1, new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    ps.setBigDecimal(2, cost);
                    return ps;
                }
            }, key);
        return (int)key.getKey().longValue();
    }

}
