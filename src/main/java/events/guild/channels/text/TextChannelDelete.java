package events.guild.channels.text;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelDelete extends ListenerAdapter {

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent deleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(deleteEvent.getGuild(), "channelDelete");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалён текстовый канал");

            logEmbed.addField("Имя", deleteEvent.getChannel().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
