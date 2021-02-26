package api.models.exceptions;

public class ChannelNotFoundException extends Exception {

    public ChannelNotFoundException(String channel) {
        super("Канал " + channel + " не найден");
    }

}