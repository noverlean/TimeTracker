package noverlin.timetracker.mappers;

import noverlin.timetracker.DTOs.ProjectDto;
import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.Project;
import noverlin.timetracker.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    Project dtoToModel(ProjectDto projectDto);

    ProjectDto modelToDto(Project project);
}
