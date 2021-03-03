package events.message;

import api.BasicEmbed;
import api.utils.Config;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageUpdate extends ListenerAdapter {

    @Override
    public void onGuildMessageUpdate(GuildMessageUpdateEvent updateEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateEvent, "messageEdit");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Сообщение изменено");
            logEmbed.addField("Сообщение до",
                    Config.MESSAGE_CACHE.get(updateEvent.getMessageIdLong()).getContentRaw());
            logEmbed.addField("Сообщение после", updateEvent.getMessage().getContentRaw());
            logEmbed.addField("Автор сообщения", updateEvent.getMessage().getAuthor().getAsTag());
            Config.MESSAGE_CACHE.remove(updateEvent.getMessageIdLong());
            Config.MESSAGE_CACHE.put(updateEvent.getMessageIdLong(), updateEvent.getMessage());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
