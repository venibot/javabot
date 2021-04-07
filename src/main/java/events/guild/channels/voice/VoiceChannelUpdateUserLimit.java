package events.guild.channels.voice;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.voice.update.VoiceChannelUpdateUserLimitEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class VoiceChannelUpdateUserLimit extends ListenerAdapter {

    @Override
    public void onVoiceChannelUpdateUserLimit(VoiceChannelUpdateUserLimitEvent updateUserLimitEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateUserLimitEvent.getGuild(), "channelUpdate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменён лимит пользователей канала");
            logEmbed.addField("Канал", updateUserLimitEvent.getChannel().getName());
            logEmbed.addField("Старый лимит",
                    (updateUserLimitEvent.getOldUserLimit() != 0 ? updateUserLimitEvent
                            .getOldUserLimit() : "Бесконечное кол-во")
                                + " участников");
            logEmbed.addField("Новый лимит",
                    (updateUserLimitEvent.getNewUserLimit() != 0 ? updateUserLimitEvent
                            .getNewUserLimit() : "Бесконечное кол-во") + " участников");

            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
