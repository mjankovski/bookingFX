package fx.booking;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.sql.SQLException;
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

    public void createAccount(String login, String pw, String firstname, String lastname, String email, String creditCardNumber, String pesel, String phoneNumber, int permissions) throws IllegalArgumentException, DuplicateKeyException {
        if(!creditCardNumber.matches("^[0-9]*$") || creditCardNumber.length()!=16) throw new IllegalArgumentException();
        if(!pesel.matches("^[0-9]*$") || pesel.length()!=11) throw new IllegalArgumentException();
        if(!phoneNumber.matches("^[0-9]*$") || phoneNumber.length()!=9) throw new IllegalArgumentException();
        if(!email.matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}.[a-zA-Z]{2,3}")) throw new IllegalArgumentException();

        jdbcTemplate.update(
                "INSERT INTO mjankovski.Uzytkownicy (LOGIN, HASLO, IMIE, NAZWISKO, EMAIL, NR_KARTY_KRED, PESEL, NR_TEL, UPRAWNIENIA) VALUES (?, ?, ?, ?, ?, ? ,? ,?, ?)",
                login, pw, firstname, lastname, email, creditCardNumber, pesel, phoneNumber, permissions
        );
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