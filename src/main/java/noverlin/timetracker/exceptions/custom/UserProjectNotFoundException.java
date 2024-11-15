package noverlin.timetracker.exceptions.custom;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Пользователя с такими данными не удалось найти. Проверьте данные и попробуйте снова.");
    }
}
