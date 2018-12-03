package fx.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public int login(String login, String pw) {
        int result = jdbcTemplate.queryForObject("select (case when(select count(LOGIN) from Administrator WHERE Administrator.LOGIN=? AND Administrator.HASLO=? = 1) THEN (select UPRAWNIENIA from Administrator WHERE Administrator.LOGIN=? AND Administrator.HASLO=?) ELSE 0 END) FROM Administrator", Integer.class,login,pw,login,pw);
        int result2 = jdbcTemplate.queryForObject("select (case when(select count(LOGIN) from Klient WHERE Klient.LOGIN=? AND Klient.HASLO=? = 1) THEN (select UPRAWNIENIA from Klient WHERE Klient.LOGIN=? AND Klient.HASLO=?) ELSE 0 END) FROM Klient", Integer.class,login,pw,login,pw);
        System.out.println(result+result2);
        return result+result2;

    }
}