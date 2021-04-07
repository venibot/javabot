package api.models.exceptions;

public class AlreadyInDatabaseException extends Exception {

    public AlreadyInDatabaseException(Object object) {
        super("Попытка добавить в базу данных " + object.toString() + ", хотя он там уже есть");
    }
}
