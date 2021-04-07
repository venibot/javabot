package api.models.exceptions;

public class MemberNotFoundException extends Exception {

    public MemberNotFoundException(String memberID, String guildID) {
        super("Пользователь " + memberID + " на сервере " + guildID + " не найден");
    }
}
