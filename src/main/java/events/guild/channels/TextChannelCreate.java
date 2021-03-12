package events.guild.channels;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextChannelCreate extends ListenerAdapter {

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent createEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(createEvent.getGuild(), "channelCreate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создан новый канал");
            logEmbed.addField("Имя", String.format("%s(%s)", createEvent.getChannel().getAsMention(), createEvent.getChannel().getName()));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
