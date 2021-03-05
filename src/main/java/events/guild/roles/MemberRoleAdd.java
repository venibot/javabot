package events.guild.roles;

import api.BasicEmbed;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MemberRoleAdd extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent roleAddEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(roleAddEvent.getGuild(), "roleAdd");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Участнику выданы роли");
            logEmbed.addField("Участник", roleAddEvent.getUser().getAsTag());
            List<String> roles = new ArrayList<>();
            for (Role role: roleAddEvent.getRoles()) {
                roles.add(role.getAsMention());
            }
            logEmbed.addField("Добавленные роли", String.join("\n", roles));
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
