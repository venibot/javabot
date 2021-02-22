package api.models.database;

import net.dv8tion.jda.api.entities.Role;
import org.bson.Document;

import javax.validation.constraints.Null;

public class User {

    private final Long userID;

    private final Long guildId;

    private String about;

    private Integer balance;

    @Null
    private Role[] roles;

    public User(Long userID, Long guildID) {
        this.userID = userID;
        this.guildId = guildID;
        this.setAbout();
        this.setBalance();
    }

    public Long getUserID() {
        return this.userID;
    }

    public Long getGuildID() {
        return this.guildId;
    }

    public String getAbout() {
        return this.about;
    }

    public Integer getBalance() {
        return this.balance;
    }

    public Role[] getRoles() {
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

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("userId", getUserID());
        document.put("guildID", getGuildID());
        document.put("about", getAbout());
        document.put("balance", getBalance());
        document.put("roles", getRoles());
        return document;
    }

    public static User fromDocument(Document document) {
        User user = new User(document.getLong("userId"), document.getLong("guildID"));
        user.setAbout(document.getString("about"));
        user.setBalance(document.getInteger("balance"));
        user.setRoles((Role[]) document.get("roles"));
        return user;
    }

    @Override
    public String toString() {
        return "Пользователь с ID " + this.userID + ", ID сервера " + this.guildId + ", Баланс " + this.balance;
    }

}
