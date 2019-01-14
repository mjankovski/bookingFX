package fx.booking.repository;

import fx.booking.dao.AccountDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    @Autowired
    private AccountDAO accountDAO;

    public ObservableList<User> getUserList() {
        ObservableList<User> userList = FXCollections.observableArrayList();

        List<Map<String,Object>> users = accountDAO.getUsersInfo();

        for (Map<String,Object> user: users) {
            userList.add(new User((String)user.get("LOGIN"), (String)user.get("IMIE"), (String)user.get("NAZWISKO"),
                    (String)user.get("EMAIL"), (BigDecimal) user.get("NR_KARTY_KRED"), (BigDecimal) user.get("PESEL"),
                    (BigDecimal) user.get("NR_TEL"), (int)user.get("UPRAWNIENIA")));
        }

        return userList;
    }
}
