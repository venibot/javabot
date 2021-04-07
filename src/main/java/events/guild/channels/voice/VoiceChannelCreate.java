package events.guild.channels.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelCreate extends ListenerAdapter {

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent createEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(createEvent.getGuild(), "channelCreate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создан новый голосовой канал");
            logEmbed.addField("Имя", createEvent.getChannel().getName());

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
