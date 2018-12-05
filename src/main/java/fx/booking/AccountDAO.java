package fx.booking;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


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
        int result = jdbcTemplate.queryForObject("select (case when(select count(LOGIN) from Administrator WHERE Administrator.LOGIN=? AND Administrator.HASLO=? = 1) THEN (select UPRAWNIENIA from Administrator WHERE Administrator.LOGIN=? AND Administrator.HASLO=?) ELSE 0 END) FROM Administrator", Integer.class,login,pw,login,pw);
        int result2 = jdbcTemplate.queryForObject("select (case when(select count(LOGIN) from Klient WHERE Klient.LOGIN=? AND Klient.HASLO=? = 1) THEN (select UPRAWNIENIA from Klient WHERE Klient.LOGIN=? AND Klient.HASLO=?) ELSE 0 END) FROM Klient", Integer.class,login,pw,login,pw);
        System.out.println(result+result2);
        return result+result2;
    }

    public void getAccountInformation(String login){
        this.login = login;
        this.firstname = jdbcTemplate.queryForObject("SELECT IMIE FROM Klient WHERE LOGIN=?", String.class,login);
        this.lastname = jdbcTemplate.queryForObject("SELECT NAZWISKO FROM Klient WHERE LOGIN=?", String.class,login);
        this.email = jdbcTemplate.queryForObject("SELECT EMAIL FROM Klient WHERE LOGIN=?", String.class,login);
        this.creditCardNumber = jdbcTemplate.queryForObject("SELECT NR_KARTY_KRED FROM Klient WHERE LOGIN=?", String.class,login);
        this.pesel = jdbcTemplate.queryForObject("SELECT PESEL FROM Klient WHERE LOGIN=?", String.class,login);
        this.phoneNumber = jdbcTemplate.queryForObject("SELECT NR_TEL FROM Klient WHERE LOGIN=?", String.class,login);
        this.permissions = jdbcTemplate.queryForObject("SELECT UPRAWNIENIA FROM Klient WHERE LOGIN=?", Integer.class,login);
    }
}