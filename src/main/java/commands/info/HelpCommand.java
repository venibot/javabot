package commands.info;

import java.util.ArrayList;
import java.util.HashMap;

import api.BasicEmbed;
import api.models.command.*;
import api.utils.DataFormatter;

@DiscordCommand(name = "help", description = "Помощь по командам бота", aliases = {"хелп"}, usage = "[Команда]",
                group = "Информация", arguments = 1)
public class HelpCommand implements Command {

    @Override
    public void doCommand(CommandContext context, String[] arguments) {

        if (arguments.length == 0) {
            BasicEmbed embedInfo = new BasicEmbed("info");
            embedInfo.setTitle("Помощь по командам бота");
            embedInfo.setFooter("<> - обязательный аргумент, [] - необязательный");
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
            
            context.sendMessage(embedInfo).queue();
        } else {
            Command command = CommandHandler.findCommand(arguments[0]);

            if (command != null) {
                DiscordCommand commandInfo = command.getCommandData();
                if (!commandInfo.hidden()) {
                    BasicEmbed commandHelp = new BasicEmbed("info");
                    commandHelp.setTitle("Помощь по команде " + commandInfo.name());
                    commandHelp.setFooter("<> - обязательный аргумент, [] - необязательный");
                    String aliases = "";

                    for (String alias: commandInfo.aliases()){
                        aliases += alias + ", ";
                    }
                    commandHelp.setDescription(commandInfo.description() + "\nАлиасы: "
                            + aliases.replaceAll(", $", "") + "\nКатегория: " + commandInfo.group()
                            .toLowerCase() + "\nИспользование: " + commandInfo.name() + " " + commandInfo.usage()
                            + "\nНеобходимые права: " + DataFormatter.getMissingPermissions(commandInfo.permissions()));
                    context.sendMessage(commandHelp).queue();

                } else {

                    context.sendMessage("Ну это вроде как скрытая команда, так что если знаешь " +
                            "про неё - должен знать, как ею пользоваться").queue();
                }
            } else {

                context.sendMessage("Команда не найдена!").queue();
            }
        }
    }
}
