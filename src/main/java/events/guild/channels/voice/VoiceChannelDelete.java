package events.guild.channels.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelDelete extends ListenerAdapter {

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent deleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(deleteEvent.getGuild(), "channelDelete");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалён голосовой канал");
            logEmbed.addField("Имя", deleteEvent.getChannel().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
