package events.guild.members.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceJoin extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent joinEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(joinEvent, "voiceJoin");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Участник вошёл в голосовй канал");
            logEmbed.addField("Участник", joinEvent.getMember().getUser().getAsTag());
            logEmbed.addField("Канал", joinEvent.getChannelJoined().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
