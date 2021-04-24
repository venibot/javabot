package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.enterprise.inject.Model;
import java.util.Date;

@Model
public class Idea {

    private final Integer ideaID;

    private final Long userID;

    private final Long guildID;

    private final String text;

    private String status;

    private Long sendTime;

    private Long acceptTime;

    private Long disagreeTime;

    private Long didTime;

    private final Long privateMessage;

    private Long publicMessage;

    public Idea(Integer ideaID, Long userID, Long guildID, String text, Long privateMessage) {
        this.ideaID = ideaID;
        this.userID = userID;
        this.guildID = guildID;
        this.text = text;
        this.privateMessage = privateMessage;
        this.status = "get";
        this.sendTime = new Date().getTime();
    }

    public Idea(Integer ideaID, Long userID, Long guildID, String text, String status, Long sendTime, Long acceptTime, Long disagreeTime, Long didTime, Long privateMessage, Long publicMessage) {
        this.ideaID = ideaID;
        this.userID = userID;
        this.guildID = guildID;
        this.text = text;
        this.status = status;
        this.sendTime = sendTime;
        this.acceptTime = acceptTime;
        this.disagreeTime = disagreeTime;
        this.didTime = didTime;
        this.privateMessage = privateMessage;
        this.publicMessage = publicMessage;
    }

    public Integer getIdeaID() {
        return this.ideaID;
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

    public Long getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getAcceptTime() {
        return this.acceptTime;
    }

    public void setAcceptTime(Long acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Long getDisagreeTime() {
        return this.disagreeTime;
    }

    public void setDisagreeTime(Long disagreeTime) {
        this.disagreeTime = disagreeTime;
    }

    public Long getDidTime() {
        return this.didTime;
    }

    public void setDidTime(Long didTime) {
        this.didTime = didTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPrivateMessage() {
        return privateMessage;
    }

    public Long getPublicMessage() {
        return publicMessage;
    }

    public void setPublicMessage(Long publicMessage) {
        this.publicMessage = publicMessage;
    }

    public BasicDBObject toDBObject() {

        BasicDBObject document = new BasicDBObject();
        document.put("ideaID", this.ideaID);
        document.put("userID", this.userID);
        document.put("guildID", this.guildID);
        document.put("text", this.text);
        document.put("status", this.status);
        document.put("sendTime", this.sendTime);
        document.put("acceptTime", this.acceptTime);
        document.put("disagreeTime", this.disagreeTime);
        document.put("didTime", this.didTime);
        document.put("privateMessage", this.privateMessage);
        document.put("publicMessage", this.publicMessage);
        return document;
    }

    public static Idea fromDBObject(DBObject document) {
        Idea idea = new Idea(
                (Integer) document.get("ideaID"),
                (Long) document.get("userID"),
                (Long) document.get("guildID"),
                (String) document.get("text"),
                (String) document.get("status"),
                (Long) document.get("sendTime"),
                (Long) document.get("acceptTime"),
                (Long) document.get("disagreeTime"),
                (Long) document.get("didTime"),
                (Long) document.get("privateMessage"),
                (Long) document.get("publicMessage")
        );
        return idea;
    }

    @Override
    public String toString() {
        return "Идея от " + this.userID + ", сервер " + this.guildID + ". Статус " + this.status + ". " + this.text;
    }
}
