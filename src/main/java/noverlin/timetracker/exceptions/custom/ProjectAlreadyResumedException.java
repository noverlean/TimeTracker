package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class ProjectAlreadyResumedException extends CustomException {
    public ProjectAlreadyResumedException() {
        super("Проект уже возобновлен.");
    }
}
