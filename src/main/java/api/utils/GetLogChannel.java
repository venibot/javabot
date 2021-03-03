package api.utils;

import api.Database;
import api.models.database.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GenericGuildEvent;

public class GetLogChannel {

    public static TextChannel getChannel(GenericGuildEvent event, String actionType) {
        Database db = new Database();
        Guild DBGuild = db.getGuildByID(event.getGuild().getIdLong());
        Long channelID = DBGuild.getLogs().get(actionType) != null ? DBGuild.getLogs().get(actionType) : null;
        if (channelID != null) {
            return event.getGuild().getTextChannelById(channelID);
        } else {
            return null;
        }
    }

}
