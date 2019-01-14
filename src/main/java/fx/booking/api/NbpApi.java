package fx.booking.api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import fx.booking.dao.CurrencyDAO;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class NbpApi {
    @Autowired
    private CurrencyDAO currencyDAO;

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

            currencyDAO.updateCurrency(currency);

            return currency;
        } catch (UnirestException e) {
            return currencyDAO.getCurrency();
        }
    }
}
