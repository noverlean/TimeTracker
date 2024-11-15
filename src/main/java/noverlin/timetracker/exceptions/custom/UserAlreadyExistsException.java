package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class UserAlreadyExistsException extends CustomException {
    public UserAlreadyExistsException() {
        super("Пользователь с такими данными уже зарегистрирован. Проверьте вводимые данные или войдите.");
    }
}
