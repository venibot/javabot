package api.models.database;

import api.utils.Config;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.enterprise.inject.Model;
import javax.validation.constraints.Digits;
import java.util.HashMap;

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

    private Boolean restoreRoles;

    private HashMap<String, Long> logs;

    private HashMap<String, Boolean> autoModeration;

    private HashMap<String, String> rolesForReactions;

    private String prefix;

    private String currency;

    public Guild(Long guildID) {

        this.guildID = guildID;
        this.welcomeMessage = "";
        this.leftMessage = "";
        this.restoreRoles = true;
        this.logs = new HashMap<>();
        this.prefix = Config.BOT_CONFIG.get("prefix");
        this.currency = "<:ignorshik:828684853657469018>";
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

    public Boolean getRestoreRoles() {
        return this.restoreRoles;
    }

    public HashMap<String, Long> getLogs() {
        return this.logs;
    }

    public HashMap<String, Boolean> getAutoModeration() {
        return this.autoModeration;
    }

    public HashMap<String, String> getRolesForReactions() {
        return this.rolesForReactions;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getCurrency() {
        return this.currency;
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

    public void setRestoreRoles(Boolean restoreRoles) {
        this.restoreRoles = restoreRoles;
    }

    public void setLogs(HashMap<String, Long> logs) {
        this.logs = logs;
    }

    public void setAutoModeration(HashMap<String, Boolean> autoModeration) {
        this.autoModeration = autoModeration;
    }

    public void setRolesForReactions(HashMap<String, String> rolesForReactions) {
        this.rolesForReactions = rolesForReactions;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
        document.put("restoreRoles", this.restoreRoles);
        document.put("logs", this.logs);
        document.put("rolesForReactions", this.rolesForReactions);
        document.put("prefix", this.prefix);
        document.put("currency", this.currency);
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
        guild.restoreRoles = (Boolean) document.get("restoreRoles");
        guild.logs = (HashMap<String, Long>) document.get("logs");
        guild.rolesForReactions = (HashMap<String, String>) document.get("rolesForReactions");
        guild.prefix = (String) document.get("prefix");
        guild.currency = (String) document.get("currency");
        return guild;
    }

    @Override
    public String toString() {
        return "Сервер с ID " + this.guildID + ", " + (this.isInGulag ? "находится в гулаге" : "не находится в гулаге");
    }
}
