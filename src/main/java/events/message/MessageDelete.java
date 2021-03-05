package events.message;

import api.BasicEmbed;
import api.utils.Config;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageDelete extends ListenerAdapter {

    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent deleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(deleteEvent.getGuild(), "messageDelete");
        if (logChannel != null) {
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
