package fx.booking.repository;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private String login;

    private String firstName;

    private String lastName;

    private String email;

    private BigDecimal creditCardNumber;

    private BigDecimal pesel;

    private BigDecimal phoneNumber;

    private int permissions;

    public User(String login, String firstName, String lastName, String email, BigDecimal creditCardNumber, BigDecimal pesel, BigDecimal phoneNumber, int permissions) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creditCardNumber = creditCardNumber;
        this.pesel = pesel;
        this.phoneNumber = phoneNumber;
        this.permissions = permissions;
    }
}
