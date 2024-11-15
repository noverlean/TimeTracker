package noverlin.timetracker.mappers;

import noverlin.timetracker.DTOs.SessionDto;
import noverlin.timetracker.entities.Session;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    Session dtoToModel(SessionDto projectDto);

    SessionDto modelToDto(Session project);
}
