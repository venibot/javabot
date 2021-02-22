import api.APIs.Monitoring;
import api.Database;
import api.models.exceptions.AlreadyInDatabaseException;
import api.utils.database.models.User;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        Database db = new Database();
        for (Guild guild : event.getJDA().getGuilds()) {
            api.utils.database.models.Guild guildModel = new api.utils.database.models.Guild(guild.getIdLong());
            try {
                db.addGuild(guildModel);
            } catch (AlreadyInDatabaseException ignored) {}
            for (Member member : guild.getMembers()) {
                User userModel = new User(member.getUser().getIdLong(), guild.getIdLong());
                if (!member.getUser().isBot()) {
                    try {
                        db.addUser(userModel);
                    } catch (AlreadyInDatabaseException ignored) {}
                }
            }
        }

        try {
            Monitoring.sendBoticordStat(event.getGuildTotalCount(), 1, event.getJDA().getUsers().size());
            Monitoring.sendSDCStat(event.getJDA().getSelfUser().getIdLong(), event.getGuildTotalCount(), 1);
        } catch (IOException e) {
            //e.printStackTrace(); мозги долбает
        }

        try {
            loadEvents(event.getJDA(), "src/main/java/events", "events");
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        System.out.println(
                event.getJDA().getSelfUser().getName() + " успешно запущен!\n" +
                        "Кол-во серверов: " + event.getGuildTotalCount() + "\n" +
                        "Пинг: " + event.getJDA().getGatewayPing() + "мс\n"
        );
    }

    private void loadEvents(JDA api, String path, String pkg) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        pkg += ".";
        File dir = new File(path);

        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".java")) {
                try {
                    ListenerAdapter adapter = (ListenerAdapter) Class.forName(pkg + file.getName().replace(".java", "")).getConstructor().newInstance();
                    api.addEventListener(adapter);
                } catch (NoSuchMethodException ignored) {
                }
            } else if (file.isDirectory()) {
                loadEvents(api, file.getPath(), pkg + file.getName());
            }
        }
    }

}
