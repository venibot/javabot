package commands.economy;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "balance", description = "Узнать баланс пользователя", aliases = {"bal", "баланс", "bal"},
                usage = "[Пользователь]", group = "Экономика", arguments = 1)
public class BalanceCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Member member = null;
        if (arguments.length == 0) {
            member = msg_event.getMember();
        } else {
            try {
                member = Converters.getMember(msg_event.getGuild(), arguments[0]);
            } catch (MemberNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                return;
            }
        }
        Database db = new Database();
        BasicEmbed infoEmbed = new BasicEmbed("info", "Баланс пользователя " + member.getEffectiveName()
                + " составляет " + db.getUserByID(member.getIdLong(), msg_event.getGuild().getIdLong()).getBalance()
                + db.getGuildByID(msg_event.getGuild().getIdLong()).getCurrency());
        msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
    }
}
