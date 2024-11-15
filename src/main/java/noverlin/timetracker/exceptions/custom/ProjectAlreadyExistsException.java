package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class ProjectAlreadyExistsException extends CustomException {
    public ProjectAlreadyExistsException() {
        super("Проект с таким названием уже зарегистрирован. Проверьте вводимые данные и попробуйте снова.");
    }
}
