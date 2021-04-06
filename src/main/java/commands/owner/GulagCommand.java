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

@DiscordCommand(name = "gulag", description = "Кидание сервера в ЧС", aliases = {"гулаг"}, usage = "gulag [Сервер]",
        group = "Владельцу", hidden = true, arguments = 2, accessLevel = 3)
public class GulagCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        Guild guild = null;
        boolean toLeave = true;
        SupportServer supportServer = new SupportServer(msg_event.getJDA());
        if (arguments.length == 0) {
            guild = msg_event.getGuild();
        } else if (arguments.length == 1) {
            if (arguments[0].equals("0") || arguments[0].equals("1")) {
                toLeave = !arguments[0].equals("0");
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
        } else {
            try {
                toLeave = !arguments[0].equals("0");
                guild = Converters.getGuild(msg_event.getJDA(), arguments[1]);
            } catch (GuildNotFoundException e) {
                msg_event.getChannel().sendMessage(new BasicEmbed("error")
                        .setDescription("Указанный сервер не найден")
                        .build()).queue();
            }
        }
        if (guild != null) {
            Database db = new Database();
            api.models.database.Guild DBGuild = db.getGuildByID(guild.getIdLong());
            if (DBGuild.getInGulag()) {
                msg_event.getChannel().sendMessage(new BasicEmbed("error")
                        .setDescription("Добавлять сервер в гулаг, хотя он там уже есть? Мило, а меня так научите?")
                        .build()).queue();
            } else {
                DBGuild.setIsInGulag(true);
                db.updateGuild(DBGuild);
                msg_event.getChannel().sendMessage(new BasicEmbed("success")
                        .setDescription("Сервер " + guild.getName() + " успешно добавлен в ЧС")
                        .build()).queue();
                if (toLeave) {
                    guild.leave().queue();
                }
                supportServer.sendGulag(guild, toLeave, msg_event.getAuthor());
            }
        }
    }

}
