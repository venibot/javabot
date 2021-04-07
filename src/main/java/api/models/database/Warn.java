package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Digits;
import java.util.Date;

@Model
public class Warn {

    @Digits(integer = 18, fraction = 0)
    private Long guildID;

    private Integer warnID;

    @Digits(integer = 18, fraction = 0)
    private Long punisherID;

    @Digits(integer = 18, fraction = 0)
    private Long intruderID;

    private String reason;

    private Long warnTime;

    private Long endTime;

    public Warn(Long guildID, Integer warnID, Long punisherID, Long intruderID, String reason, Long endTime) {

        this.guildID = guildID;
        this.warnID = warnID;
        this.punisherID = punisherID;
        this.intruderID = intruderID;
        this.reason = reason;
        this.warnTime = new Date().getTime();
        this.endTime = endTime;
    }

    public Warn(Long guildID, Integer warnID, Long punisherID, Long intruderID, String reason,
                Long warnTime, Long endTime) {

        this.guildID = guildID;
        this.warnID = warnID;
        this.punisherID = punisherID;
        this.intruderID = intruderID;
        this.reason = reason;
        this.warnTime = warnTime;
        this.endTime = endTime;
    }

    public Long getGuildID() {
        return this.guildID;
    }

    public Integer getWarnID() {
        return this.warnID;
    }

    public Long getPunisherID() {
        return this.punisherID;
    }

    public Long getIntruderID() {
        return this.intruderID;
    }

    public String getReason() {
        return this.reason;
    }

    public Long getWarnTime() {
        return this.warnTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public BasicDBObject toDBObject() {

        BasicDBObject document = new BasicDBObject();
        document.put("guildID", this.guildID);
        document.put("warnID", this.warnID);
        document.put("punisherID", this.punisherID);
        document.put("intruderID", this.intruderID);
        document.put("reason", this.reason);
        document.put("warnTime", this.warnTime);
        document.put("endTime", this.endTime);
        return document;
    }

    public static Warn fromDBObject(DBObject document) {

        Warn warn = new Warn((Long) document.get("guildID"),
                (Integer) document.get("warnID"),
                (Long) document.get("punisherID"),
                (Long) document.get("intruderID"),
                (String) document.get("reason"),
                (Long) document.get("warnTime"),
                (Long) document.get("endTime"));
        return warn;
    }

    @Override
    public String toString() {
        return "Варн пользователю " + this.intruderID + ", ID " + this.warnID + " на сервере " + this.guildID;
    }
}
