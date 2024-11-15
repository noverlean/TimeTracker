package noverlin.timetracker.exceptions.custom;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Пользователь с такими данными уже зарегистрирован. Проверьте вводимые данные или войдите.");
    }
}
