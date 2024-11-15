package unicore.api.mappers;

import org.mapstruct.Mapper;
import unicore.api.dto.UserDto;
import unicore.api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToModel(UserDto userDto);

    UserDto modelToDto(User user);
}