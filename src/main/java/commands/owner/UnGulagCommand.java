package commands.owner;

import api.BasicEmbed;
import api.Database;
import api.SupportServer;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.GuildNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "ungulag", description = "Снятие гулага с сервера", aliases = {"ангулаг"},
        group = "Владельцу", hidden = true, arguments = 1, accessLevel = 3)
public class UnGulagCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        SupportServer supportServer = new SupportServer(msg_event.getJDA());
        Guild guild = null;

        if (arguments.length == 0) {
            guild = msg_event.getGuild();
        } else {
            try {
                guild = Converters.getGuild(msg_event.getJDA(), arguments[0]);
            } catch (GuildNotFoundException e) {
                msg_event.getChannel().sendMessage(new BasicEmbed("error", "Указанный сервер не найден")
                        .build()).queue();
                return;
            }
        }

        if (guild != null) {
            Database db = new Database();
            api.models.database.Guild DBGuild = db.getGuildByID(guild.getIdLong());

            if (!DBGuild.getInGulag()) {
                msg_event.getChannel().sendMessage(new BasicEmbed("error", "Удалять сервер из гулага, хотя его там нет? Мило, а меня так научите?")
                        .build()).queue();
            } else {
                DBGuild.setIsInGulag(false);
                db.updateGuild(DBGuild);

                msg_event.getChannel().sendMessage(new BasicEmbed("success", "Сервер " + guild.getName() + " успешно удалён из ЧС")
                        .build()).queue();
                supportServer.sendUnGulag(guild, msg_event.getAuthor());
            }
        }
    }
}
