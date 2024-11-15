package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class UserNotFoundException extends CustomException {
    public UserNotFoundException() {
        super("Пользователя с такими данными не удалось найти. Проверьте данные и попробуйте снова.");
    }
}
