package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class Business {

    private Long guildID;

    private Long ownerID;

    private List<Long> workers;

    private String type;

    private Long balance;

    private String achievements;

    public Business(Long guildID, Long ownerID, String type) {
        this.guildID = guildID;
        this.ownerID = ownerID;
        this.workers = new ArrayList<>();
        this.type = type;
        this.balance = 5L;
        this.achievements = "";
    }

    public Business(Long guildID, Long ownerID, List<Long> workers, String type, Long balance, String achievements) {
        this.guildID = guildID;
        this.ownerID = ownerID;
        this.workers = workers;
        this.type = type;
        this.balance = balance;
        this.achievements = achievements;
    }

    public Long getGuildID() {
        return this.guildID;
    }

    public Long getOwnerID() {
        return this.ownerID;
    }

    public List<Long> getWorkers() {
        return this.workers;
    }

    public String getType() {
        return this.type;
    }

    public Long getBalance() {
        return this.balance;
    }

    public String getAchievements() {
        return this.achievements;
    }

    public void setWorkers(List<Long> workers) {
        this.workers = workers;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public BasicDBObject toDBObject() {

        BasicDBObject document = new BasicDBObject();
        document.put("ownerID", this.ownerID);
        document.put("guildID", this.guildID);
        document.put("workers", this.workers);
        document.put("type", this.type);
        document.put("balance", this.balance);
        document.put("achievements", this.achievements);
        return document;
    }

    public static Business fromDBObject(DBObject document) {
        Business business = new Business(
                (Long) document.get("guildID"),
                (Long) document.get("ownerID"),
                (List<Long>) document.get("workers"),
                (String) document.get("type"),
                (Long) document.get("balance"),
                (String) document.get("achievements")
        );
        return business;
    }
}
