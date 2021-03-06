package events.guild.roles;

import api.BasicEmbed;
import api.utils.Config;
import api.utils.DataFormatter;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleUpdatePermissions extends ListenerAdapter {

    @Override
    public void onRoleUpdatePermissions(RoleUpdatePermissionsEvent updatePermissionsEvent) {
        TextChannel logChannel = GetLogChannel.getChannel(updatePermissionsEvent.getGuild(), "roleUpdate");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Изменены права роли");
            logEmbed.setColor(updatePermissionsEvent.getRole().getColor());
            logEmbed.addField("Роль", updatePermissionsEvent.getRole().getName() + "(" + updatePermissionsEvent.getRole().getAsMention() + ")");
            String permissions = "";
            for (Object permission: updatePermissionsEvent.getNewPermissions().toArray()) {
                if (!updatePermissionsEvent.getOldPermissions().contains(permission)) {
                    permissions += "+ " + Config.PERMISSIONS.get(permission.toString()) + "\n";
                }
            }
            for (Object permission: updatePermissionsEvent.getOldPermissions().toArray()) {
                if (!updatePermissionsEvent.getNewPermissions().contains(permission)) {
                    permissions += "- " + Config.PERMISSIONS.get(permission.toString()) + "\n";
                }
            }
            logEmbed.addField("Права", permissions);
            logChannel.sendMessage(logEmbed.build()).queue();
        }
    }

}
