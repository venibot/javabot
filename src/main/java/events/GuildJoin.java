package events;

import api.Database;
import api.SupportServer;
import api.models.database.Guild;
import api.models.exceptions.AlreadyInDatabaseException;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildJoin extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent joinEvent) {
        Database db = new Database();
        try {
            db.addGuild(new Guild(joinEvent.getGuild().getIdLong()));
        } catch (AlreadyInDatabaseException ignored) {

        }
        User adder = null;
        if (db.getGuildByID(joinEvent.getGuild().getIdLong()).getInGulag()) {
            for (AuditLogEntry entry : joinEvent.getGuild().retrieveAuditLogs().limit(5).type(ActionType.BOT_ADD)) {
                if (entry.getTargetIdLong() == joinEvent.getJDA().getSelfUser().getIdLong()) {
                    adder = entry.getUser();
                    break;
                }
            }
            joinEvent.getGuild().leave().queue();
            SupportServer supportServer = new SupportServer();
            supportServer.sendGulagAttempt(joinEvent.getJDA(), joinEvent.getGuild(), adder);
        }
    }

}
