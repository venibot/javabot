package events.guild.members.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceLeave extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent leaveEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(leaveEvent, "voiceLeave");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Участник покинул в голосовй канал");
            logEmbed.addField("Участник", leaveEvent.getMember().getUser().getAsTag());
            logEmbed.addField("Канал", leaveEvent.getChannelLeft().getName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
