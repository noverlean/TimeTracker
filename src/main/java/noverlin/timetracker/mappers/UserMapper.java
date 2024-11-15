package noverlin.timetracker.mappers;

import noverlin.timetracker.DTOs.UserDto;
import noverlin.timetracker.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToModel(UserDto userDto);

    UserDto modelToDto(User user);
}