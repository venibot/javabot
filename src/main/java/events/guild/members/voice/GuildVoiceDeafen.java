package events.guild.members.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceDeafenEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildVoiceDeafen extends ListenerAdapter {

    @Override
    public void onGuildVoiceDeafen(GuildVoiceDeafenEvent deafenEvent) {

        if (deafenEvent.isDeafened()) {
            TextChannel logChannel = GetLogChannel.getChannel(deafenEvent.getGuild(), "voiceDeafenEvent");

            if (logChannel != null && deafenEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участнику отключён звук в голосовом канале");
                logEmbed.addField("Участник", deafenEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", deafenEvent.getVoiceState().getChannel().getName());

                logChannel.sendMessage(logEmbed.build()).queue();
            }
        } else {
            TextChannel logChannel = GetLogChannel.getChannel(deafenEvent.getGuild(), "voiceUnDeafenEvent");

            if (logChannel != null && deafenEvent.getVoiceState().getChannel() != null) {
                BasicEmbed logEmbed = new BasicEmbed("info");
                logEmbed.setTitle("Участнику включён звук в голосовом канале");
                logEmbed.addField("Участник", deafenEvent.getMember().getUser().getAsTag());
                logEmbed.addField("Канал", deafenEvent.getVoiceState().getChannel().getName());

                logChannel.sendMessage(logEmbed.build()).queue();
            }
        }
    }
}
