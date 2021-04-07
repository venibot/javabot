package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.enterprise.inject.Model;

@Model
public class Reminder {

    private Integer ID;

    private Long userID;

    private Long guildID;

    private String text;

    private Long endTime;

    public Reminder(Integer ID, Long userID, Long guildID, String text, Long endTime) {
        this.ID = ID;
        this.userID = userID;
        this.guildID = guildID;
        this.text = text;
        this.endTime = endTime;
    }

    public Integer getID() {
        return this.ID;
    }

    public Long getUserID() {
        return this.userID;
    }

    public Long getGuildID() {
        return this.guildID;
    }

    public String getText() {
        return this.text;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public BasicDBObject toDBObject() throws IllegalAccessException {

        BasicDBObject document = new BasicDBObject();
        document.put("ID", this.ID);
        document.put("userID", this.userID);
        document.put("guildID", this.guildID);
        document.put("text", this.text);
        document.put("endTime", this.endTime);
        return document;
    }

    public static Reminder fromDBObject(DBObject document) throws IllegalAccessException {
        Reminder reminder = new Reminder(

                (Integer) document.get("ID"),
                (Long) document.get("userID"),
                (Long) document.get("guildID"),
                (String) document.get("text"),
                (Long) document.get("endTime")
        );
        return reminder;
    }

    @Override
    public String toString() {
        return "Напоминание от " + this.userID + ", сервер " + this.guildID;
    }
}
