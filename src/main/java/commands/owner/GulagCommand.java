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

@DiscordCommand(name = "gulag", description = "Кидание сервера в ЧС", aliases = {"гулаг"}, usage = "gulag [Сервер]",
        group = "Владельцу", hidden = true, arguments = 2, accessLevel = 3)
public class GulagCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        Guild guild = null;
        boolean toLeave = true;
        SupportServer supportServer = new SupportServer(context.getJDA());

        if (arguments.length == 0) {
            guild = context.getGuild();
        } else if (arguments.length == 1) {
            if (arguments[0].equals("0") || arguments[0].equals("1")) {
                toLeave = !arguments[0].equals("0");
                guild = context.getGuild();
            } else {
                try {
                    guild = Converters.getGuild(context.getJDA(), arguments[0]);
                } catch (GuildNotFoundException e) {
                    context.sendMessage(new BasicEmbed("error", "Указанный сервер не найден")).queue();
                }
            }
        } else {
            try {
                toLeave = !arguments[0].equals("0");
                guild = Converters.getGuild(context.getJDA(), arguments[1]);
            } catch (GuildNotFoundException e) {
                context.sendMessage(new BasicEmbed("error", "Указанный сервер не найден")).queue();
            }
        }

        if (guild != null) {
            api.models.database.Guild DBGuild = context.getDatabaseGuild();

            if (DBGuild.getInGulag()) {
                context.sendMessage(new BasicEmbed("error", "Добавлять сервер в гулаг, хотя он там уже есть?"
                        + "Мило, а меня так научите?")).queue();
            } else {
                Database db = new Database();
                DBGuild.setIsInGulag(true);
                db.updateGuild(DBGuild);
                context.sendMessage(new BasicEmbed("success", "Сервер " + guild.getName()
                        + " успешно добавлен в ЧС")).queue();

                if (toLeave) {
                    guild.leave().queue();
                }

                supportServer.sendGulag(guild, toLeave, context.getAuthor().getUser());
            }
        }
    }
}
