package api.models.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String userID) {
        super("Пользователь " + userID + " не найден");
    }

}
