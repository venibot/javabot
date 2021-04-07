package api.models.exceptions;

public class RoleNotFoundException extends Exception {

    public RoleNotFoundException(String role) {
        super("Роль " + role + " не найдена");
    }
}