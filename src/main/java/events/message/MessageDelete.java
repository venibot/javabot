package events.message;

import api.BasicEmbed;
import api.Database;
import api.models.database.Guild;
import api.utils.Config;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDelete extends ListenerAdapter {

    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent deleteEvent) {
        Database db = new Database();
        Guild DBGuild = db.getGuildByID(deleteEvent.getGuild().getIdLong());
        if (DBGuild.getLogs().get("messageDelete") != null) {
            TextChannel logChannel = deleteEvent.getGuild().getTextChannelById(DBGuild.getLogs().get("messageDelete"));
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Сообщение удалено");
            logEmbed.addField("Контент удалённого сообщения",
                    Config.MESSAGE_CACHE.get(deleteEvent.getMessageIdLong()).getContentRaw());
            logEmbed.addField("Автор удалённого сообщения",
                    Config.MESSAGE_CACHE.get(deleteEvent.getMessageIdLong()).getAuthor().getAsTag());
            Config.MESSAGE_CACHE.remove(deleteEvent.getMessageIdLong());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
