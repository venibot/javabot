package commands.settings;

import api.BasicEmbed;
import api.Database;
import api.models.command.Command;
import api.models.command.DiscordCommand;
import api.models.database.Guild;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "prefix", description = "Установка префикса для сервера", aliases = {"префикс"},
        usage = "<Новый префикс>", group = "Настройки", arguments = 1, permissions = {Permission.MANAGE_SERVER})
public class PrefixCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            Database db = new Database();
            String prefix = db.getGuildByID(msg_event.getGuild().getIdLong()).getPrefix();
            BasicEmbed infoEmbed = new BasicEmbed("info");
            infoEmbed.setDescription("Мой текущий префикс на этом сервере - `" + prefix
                    + "` Для установки нового префикса воспользуйтесь командой " + prefix + "префикс " + new PrefixCommand().getCommandData().usage());
            msg_event.getChannel().sendMessage(infoEmbed.build()).queue();
        } else {
            if (arguments[0].contains(" ")) {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Префикс не может содержать пробелов");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                return;
            } if (arguments[0].length() > 6) {
                BasicEmbed errorEmbed = new BasicEmbed("error");
                errorEmbed.setDescription("Префикс не может быть длиннее 6 символов");
                msg_event.getChannel().sendMessage(errorEmbed.build()).queue();
                return;
            }
            Database db = new Database();
            Guild guild = db.getGuildByID(msg_event.getGuild().getIdLong());
            guild.setPrefix(arguments[0]);
            db.updateGuild(guild);
            BasicEmbed successEmbed = new BasicEmbed("success");
            successEmbed.setDescription("Префикс успешно изменён на `" + guild.getPrefix() + "`");
            msg_event.getChannel().sendMessage(successEmbed.build()).queue();
        }
    }
}
