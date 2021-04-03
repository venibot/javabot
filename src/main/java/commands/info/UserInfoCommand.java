package commands.info;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.models.exceptions.UserNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import api.utils.DataFormatter;

@DiscordCommand(name = "user", description = "Получение информации о пользователе", group = "Информация",
        aliases = {"userinfo", "юзер", "юзеринфо"}, arguments = 1, usage = "user [Пользователь]")
public class UserInfoCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        try {
            Database db = new Database();
            final Member member = arguments.length > 0 ? Converters.getMember(msg_event.getGuild(), arguments[0]) : msg_event.getMember();
            if (member == null) {
                throw new MemberNotFoundException("0", "0");
            }
            BasicEmbed userInfo = new BasicEmbed();
            userInfo.setTitle("Информация о пользователе " + member.getEffectiveName());
            userInfo.setDescription(db.getUserByID(member.getIdLong(), member.getGuild().getIdLong()).getAbout());
            userInfo.setThumbnail(member.getUser().getEffectiveAvatarUrl());
            userInfo.setFooter("ID " + member.getId());
            userInfo.addField("Полный ник", member.getUser().getAsTag(), true);
            String flags = DataFormatter.getUserFlags(member.getUser().getFlags());
            userInfo.addField("Значки", flags != "" ? flags : "Отсутствуют", true);
            userInfo.addField("Бот?", member.getIdLong() == 596613890847145985L ? "Да" : member.getUser().isBot() ? "Да" : "Нет", true);
            userInfo.addField("Аккаунт создан", DataFormatter.datetimeToString(member.getUser().getTimeCreated()), false);
            userInfo.addField("Зашёл на сервер", DataFormatter.datetimeToString(member.getTimeJoined()), true);
            if (member.getTimeBoosted() != null) {
                userInfo.addField("Забустил сервер", DataFormatter.datetimeToString(member.getTimeBoosted()), true);
            }
            userInfo.addField("Всего ролей", String.valueOf(member.getRoles().size()), false);
            userInfo.setColor(member.getColor());
            msg_event.getChannel().sendMessage(userInfo.build()).queue();
        } catch (MemberNotFoundException e) {
            try {
                BasicEmbed userInfo = new BasicEmbed("info");
                final User user = Converters.getUser(msg_event.getJDA(), arguments.length > 0 ? arguments[0] : msg_event.getAuthor().getId());
                userInfo.setTitle("Информация о пользователе " + user.getName());
                userInfo.setThumbnail(user.getEffectiveAvatarUrl());
                userInfo.setFooter("ID " + user.getId());
                String flags = DataFormatter.getUserFlags(user.getFlags());
                userInfo.addField("Значки", flags != "" ? flags : "Отсутствуют", true);
                userInfo.addField("Бот?", user.getIdLong() == 596613890847145985L ? "Да" : user.isBot() ? "Да" : "Нет", true);
                userInfo.addField("Аккаунт создан", DataFormatter.datetimeToString(user.getTimeCreated()), false);
                msg_event.getChannel().sendMessage(userInfo.build()).queue();
            }
            catch (UserNotFoundException error) {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setTitle("Указанный пользователь не обнаружен");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
            }
        }
    }

}
