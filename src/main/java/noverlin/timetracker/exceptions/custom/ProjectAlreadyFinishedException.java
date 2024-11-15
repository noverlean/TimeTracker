package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class ProjectNotFoundException extends CustomException {
    public ProjectNotFoundException() {
        super("Проекта с такими данными не удалось найти. Проверьте данные и попробуйте снова.");
    }
}
