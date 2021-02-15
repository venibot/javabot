package commands.info;

import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import api.utils.DataFormatter;
import java.awt.*;

@DiscordCommand(name = "user", description = "Получение информации о пользователе", group = "Информация", aliases = {"userinfo", "юзер", "юзеринфо"}, arguments = 1, usage = "user [Пользователь]")
public class UserInfoCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        try {
            Database db = new Database();
            final Member member = arguments.length > 0 ? Converters.getMember(msg_event.getGuild(), arguments[0]): msg_event.getMember();
            EmbedBuilder userInfo = new EmbedBuilder();
            userInfo.setTitle("Информация о пользователе " + member.getEffectiveName());
            userInfo.setDescription(db.getUserByID(msg_event.getMember().getIdLong(), msg_event.getGuild().getIdLong()).getAbout());
            userInfo.setThumbnail(member.getUser().getEffectiveAvatarUrl());
            userInfo.addField("Полный ник", member.getUser().getAsTag(), true);
            String flags = DataFormatter.getUserFlags(member.getUser().getFlags());
            userInfo.addField("Значки", flags != "" ? flags : "Отсутствуют", true);
            userInfo.addField("Бот?", member.getUser().isBot() ? "Да" : "Нет", true);
            userInfo.addField("Аккаунт создан", DataFormatter.datetimeToString(member.getUser().getTimeCreated()), false);
            userInfo.addField("Вошёл на сервер", DataFormatter.datetimeToString(member.getTimeJoined()), true);
            if (member.getTimeBoosted() != null) {
                userInfo.addField("Забустил сервер", DataFormatter.datetimeToString(member.getTimeBoosted()), true);
            }
            userInfo.addField("Всего ролей", String.valueOf(member.getRoles().size()), false);
            userInfo.setColor(member.getColor());
            msg_event.getChannel().sendMessage(userInfo.build()).queue();
        } catch (MemberNotFoundException e) {
            EmbedBuilder errorEmbed = new EmbedBuilder().setTitle("Указанный пользователь не обнаружен").setColor(Color.red);
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
