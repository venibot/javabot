package api.models.database;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Digits;

@Model
public class Guild {

    @Digits(integer = 18, fraction = 0)
    private Long guildID;

    private Boolean isInGulag = false;

    @Digits(integer = 18, fraction = 0)
    private Long muteRole;

    public Guild(Long guildID) {
        this.guildID = guildID;
    }

    public Long getGuildID() {
        return this.guildID;
    }

    public Boolean getInGulag() {
        return this.isInGulag;
    }

    public Long getMuteRole() {
        return this.muteRole;
    }

    public void setGuildID(Long guildID) {
        this.guildID = guildID;
    }

    public void setIsInGulag(Boolean inGulag) {
        isInGulag = inGulag;
    }

    public void setMuteRole(Long muteRole) {
        this.muteRole = muteRole;
    }

    public BasicDBObject toDBObject() throws IllegalAccessException {
        BasicDBObject document = new BasicDBObject();
        document.put("guildID", this.guildID);
        document.put("isInGulag", this.isInGulag);
        document.put("muteRole", this.muteRole);
        return document;
    }

    public static Guild fromDBObject(DBObject document) throws IllegalAccessException {
        Guild guild = new Guild((Long) document.get("guildID"));
        guild.isInGulag = (Boolean) document.get("isInGulag");
        guild.muteRole = (Long) document.get("muteRole");
        return guild;
    }

    @Override
    public String toString() {
        return "Сервер с ID " + this.guildID + ", " + (this.isInGulag ? "находится в гулаге" : "не находится в гулаге");
    }
}
