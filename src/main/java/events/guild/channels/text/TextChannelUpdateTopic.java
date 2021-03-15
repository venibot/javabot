package events.guild.channels.text;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.update.TextChannelUpdateTopicEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelUpdateTopic extends ListenerAdapter {

    @Override
    public void onTextChannelUpdateTopic(TextChannelUpdateTopicEvent updateTopicEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateTopicEvent.getGuild(), "channelUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено описание канала");
            logEmbed.addField("Канал", String.format("%s(%s)",
                    updateTopicEvent.getChannel().getAsMention(), updateTopicEvent.getChannel().getName()));
            logEmbed.addField("Старое описание", updateTopicEvent.getOldTopic());
            logEmbed.addField("Новое описание", updateTopicEvent.getNewTopic());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
