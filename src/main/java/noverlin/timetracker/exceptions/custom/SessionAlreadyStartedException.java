package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class SessionAlreadyStartedException extends CustomException {
    public SessionAlreadyStartedException() {
        super("Вы уже активировали сессию работы над этим проектом.");
    }
}
