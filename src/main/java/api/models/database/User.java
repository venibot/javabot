package api.models.database;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.dv8tion.jda.api.entities.Role;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

@Model
public class User {

    @Digits(integer = 18, fraction = 0)
    private Long userID;

    @Digits(integer = 18, fraction = 0)
    private Long guildID;

    @Size(min = 1, max = 500)
    private String about;

    @Digits(integer = 7, fraction = 0)
    private Integer balance;

    @Null
    private Long[] roles;

    public User(Long userID, Long guildID) {
        this.userID = userID;
        this.guildID = guildID;
        this.setAbout();
        this.setBalance();
    }

    public Long getUserID() {
        return this.userID;
    }

    public Long getGuildID() {
        return this.guildID;
    }

    public String getAbout() {
        return this.about;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public Long[] getRoles() {
        return this.roles;
    }

    public void setAbout() {
        this.about = "Установите сюда свой текст при помощи команды осебе";
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setBalance() {
        this.balance = 0;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }

    public BasicDBObject toDBObject() throws IllegalAccessException {

        BasicDBObject document = new BasicDBObject();
        document.put("userID", this.userID);
        document.put("guildID", this.guildID);
        document.put("about", this.about);
        document.put("balance", this.balance);
        document.put("roles", this.roles);
        return document;
    }

    public static User fromDBObject(DBObject document) throws IllegalAccessException {

        User user = new User((Long) document.get("userID"), (Long) document.get("guildID"));
        user.about = (String) document.get("about");
        user.balance = (Integer) document.get("balance");
        BasicDBList roles;

        if (document.get("roles") != null) {
            roles = (BasicDBList) document.get("roles");
            int i = 0;
            user.roles = new Long[roles.size()];
            for (Object roleID: roles) {
                user.roles[i] = (Long) roleID;
                i += 1;
            }
        } else {
            user.roles = new Long[0];
        }
        return user;
    }

    @Override
    public String toString() {
        return "Пользователь с ID " + this.userID + ", ID сервера " + this.guildID + ", Баланс " + this.balance;
    }
}
