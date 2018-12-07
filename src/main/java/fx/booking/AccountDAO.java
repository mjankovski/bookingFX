package fx.booking;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


@Repository
public class AccountDAO{
    private JdbcTemplate jdbcTemplate;

    @Getter
    private String login;
    @Getter
    private String firstname;
    @Getter
    private String lastname;
    @Getter
    private String email;
    @Getter
    private String creditCardNumber;
    @Getter
    private String pesel;
    @Getter
    private String phoneNumber;
    @Getter
    private int permissions;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int login(String login, String pw) {
        int result = jdbcTemplate.queryForObject("select (case when(count(LOGIN) = 1) THEN UPRAWNIENIA ELSE 0 END) FROM Uzytkownicy WHERE Uzytkownicy.LOGIN=? AND Uzytkownicy.HASLO=?", Integer.class,login,pw);
        return result;
    }

    public void getAccountInformation(String login){
        this.login = login;
        Map<String,Object> results = jdbcTemplate.queryForMap("SELECT IMIE,NAZWISKO,EMAIL,NR_KARTY_KRED,PESEL,NR_TEL,UPRAWNIENIA FROM Uzytkownicy WHERE LOGIN=?", login);
        this.firstname=(String)results.get("IMIE");
        this.lastname=(String)results.get("NAZWISKO");
        this.email=(String)results.get("EMAIL");
        this.creditCardNumber=results.get("NR_KARTY_KRED").toString();
        this.pesel=results.get("PESEL").toString();
        this.phoneNumber=results.get("NR_TEL").toString();
        this.permissions=(int)results.get("UPRAWNIENIA");
    }
}