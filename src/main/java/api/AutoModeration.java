package api;

import api.models.database.Warn;
import api.utils.Logger;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;

import java.util.HashMap;

public class AutoModeration {

    private MessageReceivedEvent msg_event;

    public AutoModeration(MessageReceivedEvent msg_event) {
        this.msg_event = msg_event;
        Database db = new Database();
        HashMap<String, Boolean> autoMod = db.getGuildByID(msg_event.getGuild().getIdLong()).getAutoModeration();
        if (autoMod != null) {
            if (autoMod.get("caps") != null && autoMod.get("caps")) {
                this.handleCaps(msg_event);
            }
        }
    }

    public AutoModeration(MessageUpdateEvent updateEvent) {
        new AutoModeration(new MessageReceivedEvent(updateEvent.getJDA(), updateEvent.getResponseNumber(), updateEvent.getMessage()));
    }

    private boolean handleCaps(MessageReceivedEvent msg_event) {
        Message message = msg_event.getMessage();
        if (message.getContentRaw().length() >= 5) {
            int capsChars = (int) message.getContentRaw().chars().filter(Character::isUpperCase).count();
            int capsPercent = capsChars / message.getContentRaw().length() * 100;
            if (capsPercent >= 50) {
                Database db = new Database();
                Warn warn = new Warn(
                        msg_event.getGuild().getIdLong(),
                        db.getLastWarnID(msg_event.getGuild().getIdLong()) + 1,
                        msg_event.getJDA().getSelfUser().getIdLong(),
                        msg_event.getAuthor().getIdLong(),
                        "Автомодерация: капс",
                        0L
                );
                db.addWarn(warn);
                Logger.logWarnCreate(warn);
                return true;
            }
        }
        return false;
    }

}
