package fx.booking.repository;

import fx.booking.dao.AccountDAO;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AccountRepository {

    @Autowired
    private AccountDAO accountDAO;

    @Getter
    private String login;
    @Getter
    @Setter
    private String firstname;
    @Getter
    @Setter
    private String lastname;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String creditCardNumber;
    @Getter
    private String pesel;
    @Getter
    @Setter
    private String phoneNumber;
    @Getter
    private int permissions;

    public void setAccountInformation(String login){
        Map<String,Object> results = accountDAO.getAccountInformation(login);
        this.login = login;
        this.firstname=(String)results.get("IMIE");
        this.lastname=(String)results.get("NAZWISKO");
        this.email=(String)results.get("EMAIL");
        this.creditCardNumber=results.get("NR_KARTY_KRED").toString();
        this.pesel=results.get("PESEL").toString();
        this.phoneNumber=results.get("NR_TEL").toString();
        this.permissions=(int)results.get("UPRAWNIENIA");
    }
}
