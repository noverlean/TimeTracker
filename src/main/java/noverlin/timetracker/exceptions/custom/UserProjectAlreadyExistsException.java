package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class UserProjectAlreadyExistsException extends CustomException {
    public UserProjectAlreadyExistsException() {
        super("Пользователь не может быть добавлен на указанный проект. Проверьте данные и попробуйте снова.");
    }
}
