package events;

import api.APIs.Boticord;
import api.APIs.SDC;
import api.Database;
import api.models.database.User;
import api.models.exceptions.AlreadyInDatabaseException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class Ready extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event)  {
        Database db = new Database();
        for (Guild guild: event.getJDA().getGuilds()) {
            api.models.database.Guild guildModel = new api.models.database.Guild(Long.parseLong(guild.getId()));
            try {
                db.addGuild(guildModel);
            } catch (AlreadyInDatabaseException e) {
                continue;
            }
            for (Member member: guild.getMembers()) {
                api.models.database.User userModel = new api.models.database.User(Long.parseLong(member.getId()), Long.parseLong(guild.getId()));
                try {
                    db.addUser(userModel);
                } catch (AlreadyInDatabaseException e) {
                    continue;
                }
            }
        }

        Boticord boticord = new Boticord();
        try {
            boticord.sendStat(event.getGuildTotalCount(), 1, event.getJDA().getUsers().size());
        } catch (IOException e) {
            e.printStackTrace();
        }

        SDC sdc = new SDC();
        try {
            sdc.sendStat(event.getJDA().getSelfUser().getIdLong(), event.getGuildTotalCount(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(
                event.getJDA().getSelfUser().getName() + " успешно запущен!\n" +
                "Кол-во серверов: " + event.getGuildTotalCount() + "\n" +
                "Пинг: " + event.getJDA().getGatewayPing() + "мс\n"
        );
    }

}
