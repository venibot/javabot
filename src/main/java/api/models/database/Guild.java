package api.models.database;

import org.bson.Document;
import org.bson.types.ObjectId;

public class Guild {

    private final ObjectId objectId;

    private boolean inGulag;

    private Long guildId;

    private Long muteRole;

    public Guild(Long guildId) {
        this.objectId = new ObjectId();
        this.guildId = guildId;
    }

    public Long getGuildId() {
        return guildId;
    }

    public void setGuildId(Long guildId) {
        this.guildId = guildId;
    }

    public boolean isInGulag() {
        return inGulag;
    }

    public void setInGulag(boolean inGulag) {
        this.inGulag = inGulag;
    }

    public Long getMuteRole() {
        return muteRole;
    }

    public void setMuteRole(Long muteRole) {
        this.muteRole = muteRole;
    }

    public Document toDocument() {
        Document document = new Document();
        document.put("guildId", this.guildId);
        document.put("isInGulag", isInGulag());
        document.put("muteRole", getMuteRole());
        return document;
    }

    public static Guild fromDocument(Document document) {
        Guild guild = new Guild(document.getLong("guildId"));
        guild.setInGulag(document.getBoolean("isInGulag"));
        guild.setMuteRole(document.getLong("muteRole"));
        return guild;
    }

    @Override
    public String toString() {
        return "Сервер с ID " + this.guildId + ", " + (this.inGulag ? "находится в гулаге" : "не находится в гулаге");
    }

}
