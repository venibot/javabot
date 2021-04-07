package events.guild;

import api.SupportServer;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeave extends ListenerAdapter {

    @Override
    public void onGuildLeave(GuildLeaveEvent leaveEvent) {
        SupportServer supportServer = new SupportServer(leaveEvent.getJDA());
        supportServer.guildLeft(leaveEvent.getGuild());
    }
}
