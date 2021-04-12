package commands.owner;

import api.BasicEmbed;
import api.Database;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.CommandContext;
import api.models.command.DiscordCommand;
import api.models.exceptions.GuildNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Guild;

@DiscordCommand(name = "ungulag", description = "Снятие гулага с сервера", aliases = {"ангулаг"},
        group = "Владельцу", hidden = true, arguments = 1, accessLevel = 3)
public class UnGulagCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {
        SupportServer supportServer = new SupportServer(context.getJDA());
        Guild guild = null;

        if (arguments.length == 0) {
            guild = context.getGuild();
        } else {
            try {
                guild = Converters.getGuild(context.getJDA(), arguments[0]);
            } catch (GuildNotFoundException e) {
                context.sendMessage(new BasicEmbed("error", "Указанный сервер не найден")).queue();
                return;
            }
        }

        if (guild != null) {
            api.models.database.Guild DBGuild = context.getDatabaseGuild();

            if (!DBGuild.getInGulag()) {
                context.sendMessage(new BasicEmbed("error", "Удалять сервер из гулага, хотя его там нет?"
                        + "Мило, а меня так научите?")).queue();
            } else {
                Database db = new Database();
                DBGuild.setIsInGulag(false);
                db.updateGuild(DBGuild);

                context.sendMessage(new BasicEmbed("success", "Сервер " + guild.getName()
                        + " успешно удалён из ЧС")).queue();
                supportServer.sendUnGulag(guild, context.getAuthor().getUser());
            }
        }
    }
}
