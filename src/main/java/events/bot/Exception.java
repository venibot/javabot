package events.bot;

import api.SupportServer;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Exception extends ListenerAdapter {

    @Override
    public void onException(ExceptionEvent exceptionEvent) {
        SupportServer supportServer = new SupportServer(exceptionEvent.getJDA());
        supportServer.sendError(exceptionEvent.getCause());
        exceptionEvent.getCause().getStackTrace();
        System.exit(0);
    }
}
