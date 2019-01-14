package fx.booking.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CurrencyDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateCurrency(BigDecimal currency){
        jdbcTemplate.update(
                "UPDATE Kursy " +
                        "SET KURS = ?, DATA_KURSU = ?" +
                        "WHERE WALUTA = ?"
                , currency, java.time.LocalDate.now(), "EUR");
    }

    public BigDecimal getCurrency(){
        return jdbcTemplate.queryForObject("SELECT KURS FROM Kursy WHERE WALUTA = ?", BigDecimal.class, "EUR");
    }
}



