package fx.booking.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NbpApi {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BigDecimal getPlnToEuroCurrency(){
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest
                    .get("http://api.nbp.pl/api/exchangerates/rates/C/EUR")
                    .asJson();
            JSONArray rates = (JSONArray) jsonResponse
                    .getBody()
                    .getObject()
                    .get("rates");

            BigDecimal currency = new BigDecimal((double)((JSONObject) rates
                    .get(0))
                    .get("bid"));

            jdbcTemplate.update(
                    "UPDATE Kursy " +
                    "SET KURS = ?, DATA_KURSU = ?" +
                    "WHERE WALUTA = ?"
                    , currency, java.time.LocalDate.now(), "EUR");

            return currency;
        } catch (UnirestException e) {
            return jdbcTemplate.queryForObject("SELECT KURS FROM Kursy WHERE WALUTA = ?", BigDecimal.class, "EUR");
        }
    }
}
