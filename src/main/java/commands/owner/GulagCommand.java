package commands.owner;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.exceptions.GuildNotFoundException;
import api.utils.Converters;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "gulag", description = "Кидание сервера в ЧС", aliases = {"гулаг"}, usage = "gulag [Сервер]",
        group = "Владельцу", hidden = true, arguments = 1)
public class GulagCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Guild guild = null;
        if (arguments.length == 0) {
            guild = msg_event.getGuild();
        } else {
            try {
                guild = Converters.getGuild(msg_event.getJDA(), arguments[0]);
            } catch (GuildNotFoundException e) {
                msg_event.getChannel().sendMessage(new BasicEmbed("error")
                        .setDescription("Указанный сервер не найден")
                        .build()).queue();
            }
        }
        if (guild != null) {
            Database db = new Database();
            api.models.database.Guild DBGuild = db.getGuildByID(guild.getIdLong());
            DBGuild.setIsInGulag(true);
            db.updateGuild(DBGuild);
            msg_event.getChannel().sendMessage(new BasicEmbed("success")
                    .setDescription("Сервер " + guild.getName() + " успешно добавлен в ЧС")
                    .build()).queue();
        }
    }

}
