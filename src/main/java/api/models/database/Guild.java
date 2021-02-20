package api.models.database;

import com.mongodb.BasicDBList;
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

    @Digits(integer = 18, fraction = 0)
    private Long[] welcomeRoles;

    private String welcomeMessage;

    private String leftMessage;

    @Digits(integer = 18, fraction = 0)
    private Long welcomeChannel;

    @Digits(integer = 18, fraction = 0)
    private Long leftChannel;

    public Guild(Long guildID) {
        this.guildID = guildID;
        this.welcomeMessage = "";
        this.leftMessage = "";
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

    public Long[] getWelcomeRoles() {
        return this.welcomeRoles;
    }

    public String getWelcomeMessage() {
        return this.welcomeMessage;
    }

    public String getLeftMessage() {
        return this.leftMessage;
    }

    public Long getWelcomeChannel() {
        return this.welcomeChannel;
    }

    public Long getLeftChannel() {
        return this.leftChannel;
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

    public void setWelcomeRoles(Long[] welcomeRoles) {
        this.welcomeRoles = welcomeRoles;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public void setLeftMessage(String leftMessage) {
        this.leftMessage = leftMessage;
    }

    public void setWelcomeChannel(Long welcomeChannel) {
        this.welcomeChannel = welcomeChannel;
    }

    public void setLeftChannel(Long leftChannel) {
        this.leftChannel = leftChannel;
    }

    public BasicDBObject toDBObject() throws IllegalAccessException {
        BasicDBObject document = new BasicDBObject();
        document.put("guildID", this.guildID);
        document.put("isInGulag", this.isInGulag);
        document.put("muteRole", this.muteRole);
        document.put("welcomeRoles", this.welcomeRoles);
        document.put("welcomeMessage", this.welcomeMessage);
        document.put("leftMessage", this.leftMessage);
        document.put("welcomeChannel", this.welcomeChannel);
        document.put("leftChannel", this.leftChannel);
        return document;
    }

    public static Guild fromDBObject(DBObject document) throws IllegalAccessException {
        Guild guild = new Guild((Long) document.get("guildID"));
        guild.isInGulag = (Boolean) document.get("isInGulag");
        guild.muteRole = (Long) document.get("muteRole");
        BasicDBList roles;
        if (document.get("welcomeRoles") != null) {
            roles = (BasicDBList) document.get("welcomeRoles");
            int i = 0;
            guild.welcomeRoles = new Long[roles.size()];
            for (Object roleID: roles) {
                guild.welcomeRoles[i] = (Long) roleID;
                i += 1;
            }
        } else {
            guild.welcomeRoles = new Long[0];
        }
        guild.welcomeMessage = (String) document.get("welcomeMessage");
        guild.leftMessage = (String) document.get("leftMessage");
        guild.welcomeChannel = (Long) document.get("welcomeChannel");
        guild.leftChannel = (Long) document.get("leftChannel");
        return guild;
    }

    @Override
    public String toString() {
        return "Сервер с ID " + this.guildID + ", " + (this.isInGulag ? "находится в гулаге" : "не находится в гулаге");
    }
}
