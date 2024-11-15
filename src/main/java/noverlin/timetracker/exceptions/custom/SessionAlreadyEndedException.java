package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class SessionAlreadyEndedException extends CustomException {
    public SessionAlreadyEndedException() {
        super("Вы уже окончили сессию работы над этим проектом.");
    }
}
