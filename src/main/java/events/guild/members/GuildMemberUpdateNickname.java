package events.guild.members;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberUpdateNickname extends ListenerAdapter {

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent updateNicknameEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updateNicknameEvent, "nicknameUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Участник изменил никнейм");
            logEmbed.addField("Участник", updateNicknameEvent.getUser().getAsTag());
            logEmbed.addField("Старый никнейм", updateNicknameEvent.getOldNickname());
            logEmbed.addField("Новый никнейм", updateNicknameEvent.getNewNickname());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
