package events.guild.members.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceStreamEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceStream extends ListenerAdapter {

    @Override
    public void onGuildVoiceStream(GuildVoiceStreamEvent streamEvent) {

        if (streamEvent.isStream()) {
            TextChannel logChannel = GetLogChannel.getChannel(streamEvent.getGuild(), "voiceStreamStart");

            if (logChannel != null && streamEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участник начал стрим");
                logEmbed.addField("Участник", streamEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", streamEvent.getVoiceState().getChannel().getName());

                logChannel.sendMessage(logEmbed.build()).queue();
            }
        } else {
            TextChannel logChannel = GetLogChannel.getChannel(streamEvent.getGuild(), "voiceStreamStop");

            if (logChannel != null && streamEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участник закончил стрим");
                logEmbed.addField("Участник", streamEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", streamEvent.getVoiceState().getChannel().getName());

                logChannel.sendMessage(logEmbed.build()).queue();
            }
        }
    }
}
