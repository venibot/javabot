package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "currency", description = "Установка валюты для экономики сервера", aliases = {"curr", "cur", "валюта"},
                usage = "<Новый значок валюты>", group = "Настройки", arguments = 1, permissions = {Permission.MANAGE_SERVER})
public class CurrencyCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Database db = new Database();
        Guild guild = db.getGuildByID(msg_event.getGuild().getIdLong());
        if (arguments.length == 0) {
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setTitle("Настройка валюты сервера");
            infoEmbed.setDescription("Текущая валюта сервера - " + guild.getCurrency() + ". Для установки новой валюты воспользуйтесь"
                    + " командой " + guild.getPrefix() + "валюта " + this.getCommandData().usage());
            msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
        } else {
            guild.setCurrency(arguments[0]);
            db.updateGuild(guild);
            msg_event.getChannel().sendMessage(new BasicEmbed("success", "Валюта успешно усановлена").build()).queue();
        }
    }
}
