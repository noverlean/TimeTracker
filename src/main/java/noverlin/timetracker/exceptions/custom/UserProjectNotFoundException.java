package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class UserProjectNotFoundException extends CustomException {
    public UserProjectNotFoundException() {
        super("Пользователь не был добавлен на этот проект. Проверьте данные и попробуйте снова.");
    }
}
