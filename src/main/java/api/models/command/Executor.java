package api.models.command;

import api.Database;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Executor extends ListenerAdapter {

    private final Set<Command> commands;
    private final Database database;

    public Executor() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        commands = new HashSet<>();
        database = new Database();
        registerCommands("src/main/java/commands", "commands");
    }

    private void registerCommands(String path, String pkg) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        pkg += ".";
        File dir = new File(path);

        for (File file: dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                try {
                    Command cmd = (Command) Class.forName(pkg + file.getName().replace(".java", "")).getConstructor().newInstance();
                    commands.add(cmd);
                } catch (NoSuchMethodException e) {
                    Command cmd = (Command) Class.forName(pkg + file.getName().replace(".java", "")).getConstructor(Set.class).newInstance(commands);
                    commands.add(cmd);
                }

            } else if (file.isDirectory()) {
                registerCommands(file.getPath(), pkg + file.getName());
            }
        }
    }

    private Command findCommand(String name) {
        return commands.stream().filter(cmd -> Arrays.asList(cmd.getCommandData().aliases()).contains(name) || cmd.getCommandData().name().equals(name)).findFirst().orElse(null);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String prefix = database.getPrefix(event.getGuild().getIdLong());
        if (prefix == null) prefix = "!"; // дефолтный

        if (event.getMessage().getContentRaw().startsWith(prefix)) {
            String message = event.getMessage().getContentRaw().replaceFirst(prefix, "").trim();
            String cmdName = message.split(" ")[0];
            Command cmd = findCommand(cmdName);

            if (cmd != null) {
                cmd.doCommand(event, message.replaceFirst(cmdName, "").trim().split(" ", 99999));
            } else {
                System.out.println("null");
            }
        }
    }
}
