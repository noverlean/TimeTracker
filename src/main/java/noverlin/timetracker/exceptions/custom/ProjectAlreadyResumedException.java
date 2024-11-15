package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class ProjectAlreadyFinishedException extends CustomException {
    public ProjectAlreadyFinishedException() {
        super("Проект уже закрыт.");
    }
}
