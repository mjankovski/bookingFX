package fx.booking.dao;

import fx.booking.helpers.Hash;
import fx.booking.repository.AccountRepository;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;


@Repository
public class AccountDAO{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @SneakyThrows
    public void createAccount(String login, String pw, String firstname, String lastname, String email, String creditCardNumber, String pesel, String phoneNumber, int permissions)
            throws InvalidCreditCardNumberException, InvalidPeselException, InvalidPhoneNumberException,
            InvalidEmailException, DuplicateKeyException, NoSuchAlgorithmException {

        checkDataFormat(login, pw, firstname, lastname, email, creditCardNumber, pesel, phoneNumber);
        byte[] salt = Hash.getSalt();
        String pwH = Hash.getSHA256(pw, salt);

        jdbcTemplate.update(
                "INSERT INTO mjankovski.Uzytkownicy (LOGIN, HASLO, IMIE, NAZWISKO, EMAIL, NR_KARTY_KRED, PESEL, NR_TEL, TOKEN, UPRAWNIENIA) VALUES (?, ?, ?, ?, ?, ? ,? ,?, ?, ?)",
                login, pwH, firstname, lastname, email, creditCardNumber, pesel, phoneNumber, salt, permissions
        );
    }

    private byte[] getSalt(String login){
        return jdbcTemplate.queryForObject("SELECT (CASE WHEN(COUNT(LOGIN)=1) THEN TOKEN ELSE 0 END) FROM Uzytkownicy WHERE LOGIN=?", byte[].class, login);
    }

    public int login(String login, String pw) {
        return jdbcTemplate.queryForObject("select (case when(count(LOGIN) = 1) THEN UPRAWNIENIA ELSE 0 END) FROM Uzytkownicy WHERE Uzytkownicy.LOGIN=? AND Uzytkownicy.HASLO=?", Integer.class,login,Hash.getSHA256(pw,getSalt(login)));
    }

    public Map<String,Object> getAccountInformation(String login){
        return jdbcTemplate.queryForMap("SELECT IMIE,NAZWISKO,EMAIL,NR_KARTY_KRED,PESEL,NR_TEL,UPRAWNIENIA FROM Uzytkownicy WHERE LOGIN=?", login);
    }

    private void checkDataFormat(String login, String pw, String firstname, String lastname, String email,
                                 String creditCardNumber, String pesel, String phoneNumber)
            throws InvalidCreditCardNumberException, InvalidPeselException, InvalidPhoneNumberException,
            InvalidEmailException {
        if(!creditCardNumber.matches("^[0-9]{16}")) throw new InvalidCreditCardNumberException();
        if(!pesel.matches("^[0-9]{11}")) throw new InvalidPeselException();
        if(!phoneNumber.matches("^[0-9]{11}")) throw new InvalidPhoneNumberException();
        if(!email.matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z]{2,3}")) throw new InvalidEmailException();
        if(!login.matches("[a-zA-Z0-9]{3,20}")) throw new IllegalArgumentException();
        if(!pw.matches("[a-zA-Z0-9]{3,20}")) throw new IllegalArgumentException();
        if(!firstname.matches("[a-zA-Z0-9ąćęłńóśźż]{2,30}")) throw new IllegalArgumentException();
        if(!lastname.matches("[a-zA-Z0-9ąćęłńóśźż]{2,30}")) throw new IllegalArgumentException();
    }

    public void checkDataFormat(String pw, String firstname, String lastname, String email,
                                String creditCardNumber, String phoneNumber)
            throws InvalidCreditCardNumberException, InvalidPhoneNumberException,
            InvalidEmailException {
        if(!creditCardNumber.matches("^[0-9]{16}")) throw new InvalidCreditCardNumberException();
        if(!phoneNumber.matches("^[0-9]{11}")) throw new InvalidPhoneNumberException();
        if(!email.matches("[a-zA-Z0-9]{3,}@[a-zA-Z0-9]{2,}\\.[a-zA-Z]{2,3}")) throw new InvalidEmailException();
        if(!pw.equals("") && !pw.matches("[a-zA-Z0-9]{3,20}")) throw new IllegalArgumentException();
        if(!firstname.matches("[a-zA-Z0-9ąćęłńóśźż]{2,30}")) throw new IllegalArgumentException();
        if(!lastname.matches("[a-zA-Z0-9ąćęłńóśźż]{2,30}")) throw new IllegalArgumentException();
    }

    public List<Map<String,Object>> getUsersInfo() {
        return jdbcTemplate.queryForList("SELECT * FROM Uzytkownicy ORDER BY NAZWISKO,IMIE");
    }

    public void deleteUser(String login){
        jdbcTemplate.update("DELETE FROM Faktury WHERE NR_FAKTURA in (select NR_FAKTURA from Rezerwacje where LOGIN=?) ", login);
        jdbcTemplate.update("DELETE FROM Uzytkownicy WHERE LOGIN = ?", login);
        jdbcTemplate.update("DELETE FROM Rezerwacje WHERE LOGIN = ?", login);
    }

    public void updateInformation(String login, String pw, String firstname, String lastname, String email,
                                  String creditCardNumber, String phoneNumber){
        byte[] salt = jdbcTemplate.queryForObject("SELECT token FROM Uzytkownicy WHERE LOGIN = ?", byte[].class, login);
        System.out.println(salt);

        if(!pw.equals("")) {
            jdbcTemplate.update("UPDATE Uzytkownicy " +
                    "SET HASLO = ?, IMIE = ?, NAZWISKO = ?, EMAIL = ?, NR_KARTY_KRED = ?, NR_TEL = ? " +
                    "WHERE LOGIN = ?", Hash.getSHA256(pw, salt), firstname, lastname, email, creditCardNumber, phoneNumber, login);
        }else{
            jdbcTemplate.update("UPDATE Uzytkownicy " +
                    "SET IMIE = ?, NAZWISKO = ?, EMAIL = ?, NR_KARTY_KRED = ?, NR_TEL = ? " +
                    "WHERE LOGIN = ?", firstname, lastname, email, creditCardNumber, phoneNumber, login);
        }
        accountRepository.setFirstname(firstname);
        accountRepository.setLastname(lastname);
        accountRepository.setEmail(email);
        accountRepository.setCreditCardNumber(creditCardNumber);
        accountRepository.setPhoneNumber(phoneNumber);
    }
}