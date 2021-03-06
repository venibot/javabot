package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberRoleRemove extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent roleRemoveEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(roleRemoveEvent.getGuild(), "roleRemove");

        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("У участника сняты роли");
            logEmbed.addField("Участник", roleRemoveEvent.getUser().getAsTag());
            List<String> roles = new ArrayList<>();

            for (Role role: roleRemoveEvent.getRoles()) {
                roles.add(role.getAsMention());
            }

            logEmbed.addField("Снятые роли", String.join("\n", roles));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }
}
