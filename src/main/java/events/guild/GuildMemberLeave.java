package events.guild;

import api.BasicEmbed;
import api.Database;
import api.TemplateEngine;
import api.models.database.Guild;
import api.models.database.User;
import api.utils.DataFormatter;
import api.utils.GetLogChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class GuildMemberLeave extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent removeEvent) {
        Database db = new Database();
        Guild DBGuild = db.getGuildByID(removeEvent.getGuild().getIdLong());

        TextChannel logChannel = GetLogChannel.getChannel(removeEvent, "memberLeave");
        if (logChannel != null) {
            BasicEmbed logEmbed = new BasicEmbed("info");
            logEmbed.setTitle("Участник покинул сервер");
            logEmbed.addField("Тег", removeEvent.getUser().getAsTag());
            logEmbed.addField("Аккаунт создан", DataFormatter.datetimeToString(removeEvent.getUser().getTimeCreated()));
            logEmbed.addField("Был на сервере с", DataFormatter.datetimeToString(removeEvent.getMember().getTimeJoined()));
            logEmbed.setThumbnail(removeEvent.getUser().getEffectiveAvatarUrl());
            logChannel.sendMessage(logEmbed.build()).queue();
        }

        if (!DBGuild.getLeftMessage().equals("") && DBGuild.getLeftChannel() != null) {
            TextChannel leftChannel = removeEvent.getJDA().getTextChannelById(DBGuild.getLeftChannel());
            HashMap<String, String> values = new HashMap<>();
            values.put("member.tag", removeEvent.getMember().getUser().getAsTag());
            values.put("member.mention", removeEvent.getMember().getAsMention());
            values.put("guild.memberCount", String.valueOf(removeEvent.getGuild().getMemberCount()));
            leftChannel.sendMessage(TemplateEngine.render(DBGuild.getLeftMessage(), values)).queue();
        }
        if (DBGuild.getRestoreRoles()) {
            User DBUser = db.getUserByID(removeEvent.getMember().getIdLong(), removeEvent.getGuild().getIdLong());
            Long[] roles = new Long[removeEvent.getMember().getRoles().size()];
            int i = 0;
            for (Role role: removeEvent.getMember().getRoles()) {
                roles[i] = role.getIdLong();
                i += 1;
            }
            DBUser.setRoles(roles);
            db.updateUser(DBUser);
        }
    }

}
