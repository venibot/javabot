package api.models;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public interface SupportServer {

    Guild getGuild(JDA bot);

    boolean isTester(JDA bot, User user);

    boolean isSupport(JDA bot, User user);

    boolean isStaff(JDA bot, User user);

    boolean isDeveloper(JDA bot, User user);

    boolean isDonator(JDA bot, User user);

    void sendError(JDA bot, Exception error);

}
