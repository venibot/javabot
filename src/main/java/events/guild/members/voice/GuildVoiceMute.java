package events.guild.members.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceMute extends ListenerAdapter {

    @Override
    public void onGuildVoiceMute(GuildVoiceMuteEvent muteEvent) {
        if (muteEvent.isMuted()) {
            TextChannel logChannel = GetLogChannel.getChannel(muteEvent, "voiceMuteEvent");
            if (logChannel != null && muteEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участник замьючен в голосовом канале");
                logEmbed.addField("Участник", muteEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", muteEvent.getVoiceState().getChannel().getName());
                logChannel.sendMessage(logEmbed.build()).queue();
            }
        } else {
            TextChannel logChannel = GetLogChannel.getChannel(muteEvent, "voiceUnMuteEvent");
            if (logChannel != null && muteEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участник размьючен в голосовом канале");
                logEmbed.addField("Участник", muteEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", muteEvent.getVoiceState().getChannel().getName());
                logChannel.sendMessage(logEmbed.build()).queue();
            }
        }
    }

}
