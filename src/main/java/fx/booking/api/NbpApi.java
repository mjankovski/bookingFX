package fx.booking.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NbpApi {
    private HttpResponse<JsonNode> jsonResponse;

    public BigDecimal getPlnToEuroCurrency(){
        try {
            jsonResponse = Unirest.get("http://api.nbp.pl/api/exchangerates/rates/C/EUR")
                    .asJson();
        } catch (
                UnirestException e) {
            e.printStackTrace();
        }

        JSONArray rates = (JSONArray) jsonResponse.getBody().getObject().get("rates");
        return new BigDecimal((double)((JSONObject) rates.get(0)).get("bid"));
    }
}
