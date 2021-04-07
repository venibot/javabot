package events.guild.invites;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildInviteCreate extends ListenerAdapter {

    @Override
    public void onGuildInviteCreate(GuildInviteCreateEvent inviteCreateEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(inviteCreateEvent.getGuild(), "inviteCreate");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Создано новое приглашение");
            logEmbed.addField("Канал", inviteCreateEvent.getChannel().getName());

            logEmbed.addField("Кто создал", String.format("%s(%s)",
                    inviteCreateEvent.getInvite().getInviter().getAsMention(), inviteCreateEvent.getInvite()
                            .getInviter().getAsTag()));

            logEmbed.addField("Максимальное количество использований",
                    inviteCreateEvent.getInvite().getMaxUses() != 0 ? String.valueOf(inviteCreateEvent.getInvite()
                            .getMaxUses()) : "Не ограничено");

            logEmbed.addField("Созданное приглашение", inviteCreateEvent.getInvite().getUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
