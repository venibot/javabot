package events.guild.channels.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelUpdateName extends ListenerAdapter {

    @Override
    public void onVoiceChannelUpdateName(VoiceChannelUpdateNameEvent updateNameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNameEvent.getGuild(), "channelUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменено имя голосового канала");
            logEmbed.addField("Канал", updateNameEvent.getNewName());
            logEmbed.addField("Старое имя", updateNameEvent.getOldName());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
