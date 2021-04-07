package api.utils;

import api.Database;
import api.models.database.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class GetLogChannel {

    public static TextChannel getChannel(net.dv8tion.jda.api.entities.Guild guild, String actionType) {

        Database db = new Database();
        Guild DBGuild = db.getGuildByID(guild.getIdLong());
        Long channelID = DBGuild.getLogs().get(actionType) != null ? DBGuild.getLogs().get(actionType) : null;

        if (channelID != null) {
            return guild.getTextChannelById(channelID);
        } else {
            return null;
        }
    }
}
