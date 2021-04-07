package api.models.exceptions;

public class GuildNotFoundException extends Exception {

    public GuildNotFoundException(String guildID) {
        super("Сервер " + guildID + " не найден");
    }
}
