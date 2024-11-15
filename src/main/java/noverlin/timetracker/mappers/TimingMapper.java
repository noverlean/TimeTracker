package noverlin.timetracker.mappers;

import noverlin.timetracker.DTOs.TimingDto;
import noverlin.timetracker.entities.Timing;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TimingMapper {
    Timing dtoToModel(TimingDto projectDto);

    TimingDto modelToDto(Timing project);
}
