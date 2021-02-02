package commands.info;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import api.models.command.Command;
import api.models.command.CommandHandler;
import api.models.command.DiscordCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordCommand(name = "help", description = "Помощь по командам бота", aliases = {"хелп"}, group = "Информация", arguments = 1)
public class HelpCommand implements Command {

    @Override
    public void doCommand(MessageReceivedEvent msg_event, String[] arguments) {
        if (arguments.length == 0) {
            EmbedBuilder embedInfo = new EmbedBuilder();
            embedInfo.setTitle("Помощь по командам бота");
            embedInfo.setColor(Color.blue);
            HashMap<String, ArrayList<Command>> commands = new HashMap<>();
            for (Command command: CommandHandler.commands) {
                DiscordCommand commandInfo = command.getCommandData();
                if (!commandInfo.hidden()){
                    if (!commands.containsKey(commandInfo.group())) {
                        commands.put(commandInfo.group(), new ArrayList<Command>());
                        commands.get(commandInfo.group()).add(command);
                    }
                    else {
                        commands.get(commandInfo.group()).add(command);
                    }
                }
            }
            for (String key: commands.keySet()) {
                String groupCommands = "";
                for (Command command: commands.get(key)){
                    groupCommands += command.getCommandData().name() + ", ";
                }
                embedInfo.addField(key, groupCommands.replaceAll(", $", ""), false);
            }
            
            msg_event.getChannel().sendMessage(embedInfo.build()).queue();
        } else {
            System.out.println(arguments[0]);
            Command command = CommandHandler.findCommand(arguments[0]);
            if (command != null) {
                DiscordCommand commandInfo = command.getCommandData();
                if (!commandInfo.hidden()) {
                    EmbedBuilder commandHelp = new EmbedBuilder();
                    commandHelp.setTitle("Помощь по команде " + commandInfo.name());
                    commandHelp.setColor(Color.blue);
                    String aliases = "";
                    for (String alias: commandInfo.aliases()){
                        aliases += alias + ", ";
                    }
                    commandHelp.setDescription(commandInfo.description() + "\n Алиасы: " + aliases.replaceAll(", $", "") + "\n Категория: " + commandInfo.group().toLowerCase() + "\n Использование: .." + commandInfo.name() + " " + commandInfo.usage());
                    msg_event.getChannel().sendMessage(commandHelp.build()).queue();
                } else {
                    msg_event.getChannel().sendMessage("Ну это вроде как скрытая команда, так что если знаешь про неё - должен знать, как ею пользоваться").queue();
                }
            } else {
                msg_event.getChannel().sendMessage("Команда не найдена!").queue();
            }
        }
    }

}
