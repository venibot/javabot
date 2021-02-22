package api.models;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public interface SupportServer {

    Guild getGuild();

    boolean isTester(User user);

    boolean isSupport(User user);

    boolean isStaff(User user);

    boolean isDeveloper(User user);

    boolean isDonator(User user);

    void sendError(Exception error);

    void sendGulag(Guild guild, boolean left, User gulagger);

    void sendGulagAttempt(Guild guild, User adder);

}
