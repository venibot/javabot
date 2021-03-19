package events.guild.invites;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildInviteDelete extends ListenerAdapter {

    @Override
    public void onGuildInviteDelete(GuildInviteDeleteEvent inviteDeleteEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(inviteDeleteEvent.getGuild(), "inviteDelete");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Удалено приглашение");
            logEmbed.addField("Удалённое приглашение", inviteDeleteEvent.getUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
