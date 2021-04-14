package commands.economy;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.database.User;
import api.models.exceptions.MemberNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;

@DiscordCommand(name = "add-money", description = "Выдача денег пользователю", aliases = {"addmoney", "датьденьги"},
                usage = "<Пользователь> <Сумма для выдачи>", group = "Экономика", arguments = 2,
                permissions = {Permission.MANAGE_SERVER})
public class AddMoneyCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        if (arguments.length == 0) {
            BasicEmbed errorEmbed = new BasicEmbed("error", "Укажите пользователя, которому "
                    + "необходимо выдать деньги и необходимую сумму");
            context.sendMessage(errorEmbed).queue();
        } else {
            try {
                Member member = Converters.getMember(context.getGuild(), arguments[0]);
                Integer sum = Integer.parseInt(arguments[1]);
                if (sum < 1) {
                    BasicEmbed errorEmbed = new BasicEmbed("error", "Сумма для выдачи не может " +
                            "быть меньше нуля");
                    context.sendMessage(errorEmbed).queue();
                    return;
                }
                Database db = new Database();
                User dbUser = db.getUserByID(member.getIdLong(), context.getGuild().getIdLong());
                dbUser.setBalance(dbUser.getBalance() + sum);
                db.updateUser(dbUser);
                BasicEmbed successEmbed = new BasicEmbed("success", "Пользователю "
                        + member.getEffectiveName() + " успешно выдано "
                        + sum + context.getDatabaseGuild().getCurrency() + " Теперь его баланс составляет "
                        + dbUser.getBalance() + context.getDatabaseGuild().getCurrency());
                context.sendMessage(successEmbed).queue();
            } catch (MemberNotFoundException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Указанный пользователь не найден");
                context.sendMessage(errorEmbed).queue();
            } catch (NumberFormatException e) {
                BasicEmbed errorEmbed = new BasicEmbed("error", "Неверно указана сумма для выдачи");
                context.sendMessage(errorEmbed).queue();
            }
        }
    }
}
