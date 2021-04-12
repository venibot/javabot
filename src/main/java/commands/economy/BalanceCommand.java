package commands.economy;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.exceptions.MemberNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Member;

@DiscordCommand(name = "balance", description = "Узнать баланс пользователя", aliases = {"bal", "баланс", "bal"},
                usage = "[Пользователь]", group = "Экономика", arguments = 1)
public class BalanceCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        Member member = null;
        if (arguments.length == 0) {
            member = context.getAuthor();
        } else {
            try {
                member = Converters.getMember(context.getGuild(), arguments[0]);
            } catch (MemberNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                context.sendMessage(errorEmbed).queue();
                return;
            }
        }
        Database db = new Database();
        BasicEmbed infoEmbed = new BasicEmbed("info", "Баланс пользователя " + member.getEffectiveName()
                + " составляет " + db.getUserByID(member.getIdLong(), context.getGuild().getIdLong()).getBalance()
                + context.getDatabaseGuild().getCurrency());
        context.sendMessage(infoEmbed).queue();
    }
}
