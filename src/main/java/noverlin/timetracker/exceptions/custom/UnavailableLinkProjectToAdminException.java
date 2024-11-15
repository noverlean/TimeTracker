package noverlin.timetracker.exceptions.custom;

import noverlin.timetracker.exceptions.CustomException;

public class UnavailableLinkProjectToAdminException extends CustomException {
    public UnavailableLinkProjectToAdminException() {
        super("Пользователя с правами администратора невозможно привязаьт к проекту. Проверьте вводимые данные.");
    }
}
