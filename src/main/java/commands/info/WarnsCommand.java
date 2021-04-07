package commands.info;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Warn;
import api.models.exceptions.MemberNotFoundException;
import api.utils.Converters;
import api.utils.DataFormatter;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@DiscordCommand(name = "warns", description = "Посмотреть варны пользователя",
        aliases = {"варны", "преды"}, usage = "[Пользователь]",
                group = "Информация", arguments = 1)
public class WarnsCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {

        try {
            Member member = arguments.length > 0 ? Converters
                    .getMember(msg_event.getGuild(), arguments[0]) : msg_event.getMember();

            Database db = new Database();
            List<Warn> warns = db.getMemberWarns(msg_event.getGuild().getIdLong(), member.getIdLong());
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Предупреждения пользователя " + member.getEffectiveName());

            if (warns.size() == 0) {
                infoEmbed.setDescription("Предупреждения отсутствуют");
            } else {
                infoEmbed.setDescription("Всего предупреждений " + warns.size());

                for (Warn warn : warns) {
                    infoEmbed.addField("ID " + warn.getWarnID() + " - " + msg_event
                                    .getJDA().getUserById(warn.getPunisherID()).getAsTag() + " - " + warn.getReason(),
                            "Предупреждение выдано " + DataFormatter
                                    .datetimeToString(DataFormatter.unixToDateTime(warn.getWarnTime())) + ". "
                                    + (warn.getEndTime() != 0 ? "Предупреждение истечёт "
                                    + DataFormatter.datetimeToString(DataFormatter
                                            .unixToDateTime(warn.getEndTime())) : "Предупреждение перманентное"));
                }
            }

            msg_event.getChannel().sendMessage(infoEmbed.build()).queue();

        } catch (MemberNotFoundException e) {
            BasicEmbed errorEmbed = new BasicEmbed("error");
            errorEmbed.setDescription("Указанный пользователь не найден");
            msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
        }
    }
}
